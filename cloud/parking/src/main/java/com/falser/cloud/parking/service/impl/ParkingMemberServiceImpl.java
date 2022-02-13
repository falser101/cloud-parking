package com.falser.cloud.parking.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.common.exception.BaseException;
import com.falser.cloud.parking.dao.ParkingMemberDao;
import com.falser.cloud.parking.entity.ParkingMember;
import com.falser.cloud.parking.entity.ParkingParkingSpace;
import com.falser.cloud.parking.enums.UsingStatusEnum;
import com.falser.cloud.parking.service.ParkingMemberService;
import com.falser.cloud.parking.service.ParkingParkingSpaceService;
import com.falser.cloud.parking.vo.ParkingMemberAddVO;
import com.falser.cloud.parking.vo.ParkingMemberUpdateVO;
import com.falser.cloud.parking.vo.ParkingMemberVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 停车场会员(ParkingMember)表服务实现类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Service
public class ParkingMemberServiceImpl extends ServiceImpl<ParkingMemberDao, ParkingMember> implements ParkingMemberService {

    private final ParkingParkingSpaceService parkingParkingSpaceService;

    public ParkingMemberServiceImpl(ParkingParkingSpaceService parkingParkingSpaceService) {
        this.parkingParkingSpaceService = parkingParkingSpaceService;
    }

    @Override
    public void addMember(ParkingMemberAddVO vo) {
        // 检查车位是否已被占用或者已被禁用
        ParkingMember parkingMember = getParkingMember(vo);
        parkingMember.setActiveTime(LocalDateTime.now());
        save(parkingMember);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByVO(ParkingMemberUpdateVO vo) {
        ParkingMember parkingMember = getParkingMember(vo);
        ParkingMember member = getById(parkingMember.getId());
        String parkingSpaceNum = member.getParkingSpaceNum();
        parkingParkingSpaceService.update(new UpdateWrapper<ParkingParkingSpace>()
                .lambda()
                .set(ParkingParkingSpace::getUsingStatus, UsingStatusEnum.UNUSED.name())
                .eq(ParkingParkingSpace::getParkingSpaceNum, parkingSpaceNum));
        updateById(parkingMember);
    }

    private ParkingMember getParkingMember(ParkingMemberVO vo) {
        String parkingSpaceNum = vo.getParkingSpaceNum();
        ParkingParkingSpace parkingSpace = parkingParkingSpaceService.getOne(new LambdaQueryWrapper<ParkingParkingSpace>()
                .eq(ParkingParkingSpace::getParkingSpaceNum, parkingSpaceNum)
                .eq(ParkingParkingSpace::getUsingStatus, UsingStatusEnum.UNUSED.name())
                .eq(ParkingParkingSpace::getStatus, StatusEnum.ENABLE.name())
        );
        if (Objects.isNull(parkingSpace)) {
            throw new BaseException("未查询到可分配的车位");
        }
        parkingSpace.setUsingStatus(UsingStatusEnum.IN_USE);
        parkingParkingSpaceService.updateById(parkingSpace);
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        ParkingMember parkingMember = new ParkingMember();
        BeanUtils.copyProperties(vo, parkingMember, "memberType", "sex");
        parkingMember.setCreateBy(userinfo.getUserInfo().getUsername());
        parkingMember.setSex(vo.getSex());
        parkingMember.setMemberType(vo.getMemberType());
        return parkingMember;
    }
}

