package com.falser.cloud.parking.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.common.util.FileUtil;
import com.falser.cloud.parking.alipay.AlipayTrade;
import com.falser.cloud.parking.entity.ParkingAccessInfo;
import com.falser.cloud.parking.entity.ParkingMember;
import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.entity.ParkingParkingSpace;
import com.falser.cloud.parking.enums.*;
import com.falser.cloud.parking.mq.OrderTradeStatusMqProducer;
import com.falser.cloud.parking.mq.dto.ParkingOrderDTO;
import com.falser.cloud.parking.service.*;
import com.falser.cloud.parking.utils.QRCodeUtil;
import com.falser.cloud.parking.vo.CarInVO;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.LicensePlateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.LicensePlateOCRResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CarIdentifyServiceImpl implements CarIdentifyService {

    private final ParkingMemberService parkingMemberService;

    private final ParkingParkingSpaceService parkingParkingSpaceService;

    private final ParkingAccessInfoService parkingAccessInfoService;

    private final ParkingOrderService parkingOrderService;

    private final AlipayTrade alipayTrade;

    private final OrderTradeStatusMqProducer orderTradeStatusMqProducer;

    private final FileUtil fileUtil;

    public CarIdentifyServiceImpl(@Lazy ParkingMemberService parkingMemberService,
                                  @Lazy ParkingParkingSpaceService parkingParkingSpaceService,
                                  @Lazy ParkingAccessInfoService parkingAccessInfoService,
                                  @Lazy ParkingOrderService parkingOrderService,
                                  @Lazy AlipayTrade alipayTrade,
                                  @Lazy OrderTradeStatusMqProducer orderTradeStatusMqProducer,
                                  @Lazy FileUtil fileUtil) {
        this.parkingMemberService = parkingMemberService;
        this.parkingParkingSpaceService = parkingParkingSpaceService;
        this.parkingAccessInfoService = parkingAccessInfoService;
        this.parkingOrderService = parkingOrderService;
        this.alipayTrade = alipayTrade;
        this.orderTradeStatusMqProducer = orderTradeStatusMqProducer;
        this.fileUtil = fileUtil;
    }

    @Override
    public String getCarNum(String imgUrl) throws TencentCloudSDKException {
        // 密匙id
        Credential cred = new Credential("AKIDWorfEXrXHm26ysPy71f3MDVYLsAJYpLm", "LQHtFLCvYv2ulMhT0KPEn45qLZYIp6IQ");
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 客户端配置
        OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
        // 图片路径
        String params = "{\"ImageUrl\":" + "\"" + imgUrl + "\"" + "}";
        System.out.println(params);
        // 进行扫描
        LicensePlateOCRRequest req = LicensePlateOCRRequest.fromJsonString(params, LicensePlateOCRRequest.class);
        //扫描结果
        LicensePlateOCRResponse resp = client.LicensePlateOCR(req);

        log.info("扫描结果:{}", LicensePlateOCRRequest.toJsonString(resp));
        return resp.getNumber();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void carIn(CarInVO vo) {
        // 查询车辆所属会员类型
        String carNum = vo.getCarNum();
        // 检查是否已经进场且未出场
        ParkingAccessInfo info = parkingAccessInfoService.getOne(new LambdaQueryWrapper<ParkingAccessInfo>()
                .eq(ParkingAccessInfo::getLicensePlateNumber, carNum)
                .isNull(ParkingAccessInfo::getOutTime));
        if (Objects.nonNull(info)) {
            throw new BaseException("车辆已经入场且还未出场！");
        }

        ParkingMember parkingMember = parkingMemberService.getOne(new LambdaQueryWrapper<ParkingMember>()
                .eq(ParkingMember::getLicensePlateNumber, carNum));
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        if (Objects.isNull(parkingMember)) {
            // 新建临时会员
            parkingMember = new ParkingMember();
            parkingMember.setMemberType(MemberTypeEnum.TEMPORARY);
            parkingMember.setLicensePlateNumber(carNum);
            parkingMemberService.save(parkingMember);
        }

        if (Objects.equals(MemberTypeEnum.TEMPORARY.name(), parkingMember.getMemberType())) {
            // 检查有无可用车位
            List<ParkingParkingSpace> parkingSpaces = parkingParkingSpaceService.list(new LambdaQueryWrapper<ParkingParkingSpace>()
                    .eq(ParkingParkingSpace::getUsingStatus, UsingStatusEnum.UNUSED.name())
                    .eq(ParkingParkingSpace::getType, ParkingTypeEnum.TEMPORARY.name())
            );
            if (CollectionUtils.isEmpty(parkingSpaces)) {
                throw new BaseException("暂无可用停车位!");
            }
            ParkingParkingSpace parkingParkingSpace = parkingSpaces.get(0);
            parkingMember.setParkingSpaceNum(parkingParkingSpace.getParkingSpaceNum());
            parkingParkingSpace.setUsingStatus(UsingStatusEnum.IN_USE);
            parkingParkingSpaceService.updateById(parkingParkingSpace);
            parkingMemberService.updateById(parkingMember);
        }
        // 新增出入信息表
        ParkingAccessInfo accessInfo = new ParkingAccessInfo();
        accessInfo.setLicensePlateNumber(carNum);
        accessInfo.setEntryTime(LocalDateTime.now());
        accessInfo.setParkingSpaceNum(parkingMember.getParkingSpaceNum());
        accessInfo.setType(parkingMember.getMemberType().getParkingTypeEnum());
        accessInfo.setCreateBy(userinfo.getUserInfo().getUsername());
        parkingAccessInfoService.save(accessInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParkingOrder carOut(String carNum) throws Exception {
        ParkingMember parkingMember = parkingMemberService.getOne(new LambdaQueryWrapper<ParkingMember>()
                .eq(ParkingMember::getLicensePlateNumber, carNum));

        if (MemberTypeEnum.TEMPORARY.equals(parkingMember.getMemberType())) {
            ParkingOrder parkingOrder = parkingOrderService.getOne(new LambdaQueryWrapper<ParkingOrder>()
                    .eq(ParkingOrder::getCustomerId, parkingMember.getId())
                    .in(ParkingOrder::getStatus, OrderStatusEnum.NEW.name(), OrderStatusEnum.ONGOING.name()));
            if (Objects.isNull(parkingOrder)) {
                ParkingAccessInfo accessInfo = parkingAccessInfoService.getOne(new LambdaQueryWrapper<ParkingAccessInfo>()
                        .eq(ParkingAccessInfo::getLicensePlateNumber, carNum)
                        .isNull(ParkingAccessInfo::getOutTime));
                if (Objects.isNull(accessInfo)) {
                    throw new BaseException("当前车辆未进行入场");
                }
                LocalDateTime now = LocalDateTime.now();
                accessInfo.setOutTime(now);
                parkingAccessInfoService.updateById(accessInfo);

                long hours = Duration.between(accessInfo.getEntryTime(), accessInfo.getOutTime()).toHours();
                parkingOrder = getParkingOrder(parkingMember, now, hours);
                parkingOrderService.save(parkingOrder);
            }
            // 服务端发起下单后就通过mq向支付宝发起交易查询请求，
            sendTradeQuery(parkingOrder, orderTradeStatusMqProducer);
            return parkingOrder;
        }

        if (MemberTypeEnum.TOP_UP.equals(parkingMember.getMemberType())) {
            // 计算费用生成支付订单

        }
        return null;
    }

    /**
     * 发送交易查询
     *
     * @param parkingOrder               停车顺序
     * @param orderTradeStatusMqProducer 订单贸易地位mq生产国
     */
    private void sendTradeQuery(ParkingOrder parkingOrder, OrderTradeStatusMqProducer orderTradeStatusMqProducer) {
        ParkingOrderDTO parkingOrderDTO = new ParkingOrderDTO();
        parkingOrderDTO.setId(parkingOrder.getId());
        parkingOrderDTO.setTimes(1);
        orderTradeStatusMqProducer.sendMsg(parkingOrderDTO);
    }

    /**
     * 得到停车秩序
     *
     * @param parkingMember 泊车员
     * @param now           现在
     * @param hours         小时
     * @return {@link ParkingOrder}
     * @throws Exception 异常
     */
    private ParkingOrder getParkingOrder(ParkingMember parkingMember, LocalDateTime now, long hours) throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setCustomerId(parkingMember.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        parkingOrder.setOrderNo(now.format(formatter));
        parkingOrder.setPayType(PayTypeEnum.ON_LINE);
        parkingOrder.setPayChannel(PayChannelTypeEnum.ALIPAY);
        parkingOrder.setOrderMoney(new BigDecimal(String.valueOf(3 * hours)).setScale(2, RoundingMode.HALF_EVEN));
        parkingOrder.setDiscountMoney(new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN));
        parkingOrder.setPayableAmount(parkingOrder.getOrderMoney().subtract(parkingOrder.getDiscountMoney()).setScale(2, RoundingMode.HALF_EVEN));
        parkingOrder.setStatus(OrderStatusEnum.NEW);
        // 计算费用生成支付订单
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(parkingOrder.getOrderNo());
        model.setTotalAmount(parkingOrder.getPayableAmount().toPlainString());
        model.setSubject("智能化停车场临时停车收费");
        AlipayTradePrecreateResponse response = alipayTrade.getQrCode(model);
        log.info("生成支付订单:{}", JSON.toJSONString(response));
        // 向外返回qrCode给用户扫描
        String content = response.getQrCode();
        File file = new File("qrCode.jpg");
        QRCodeUtil.encode(content, new FileOutputStream(file));
        QRCodeUtil.decode(file);
        // 文件上传返回url，前端展示二维码
        // 返回订单id以便查询支付状态
        parkingOrder.setAlipayQrCode(fileUtil.upload(file));
        return parkingOrder;
    }
}
