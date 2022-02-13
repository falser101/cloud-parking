package com.falser.cloud.parking.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.service.ParkingOrderService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 出场订单主表(ParkingOrder)表控制层
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Api(tags = "出场订单主表")
@RestController
@RequestMapping("/api/parking/order")
public class ParkingOrderController {
    /**
     * 服务对象
     */
    @Resource
    private ParkingOrderService parkingOrderService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param parkingOrder 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResponse selectAll(Page<ParkingOrder> page, ParkingOrder parkingOrder) {
        return ApiResponse.ofSuccess(this.parkingOrderService.page(page, new QueryWrapper<>(parkingOrder)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResponse selectOne(@PathVariable Serializable id) {
        return ApiResponse.ofSuccess(this.parkingOrderService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param parkingOrder 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResponse insert(@RequestBody ParkingOrder parkingOrder) {
        return ApiResponse.ofSuccess(this.parkingOrderService.save(parkingOrder));
    }

    /**
     * 修改数据
     *
     * @param parkingOrder 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ApiResponse update(@RequestBody ParkingOrder parkingOrder) {
        return ApiResponse.ofSuccess(this.parkingOrderService.updateById(parkingOrder));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResponse delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.parkingOrderService.removeByIds(idList));
    }

    // https://bashuculture-1301017257.cos.ap-chengdu.myqcloud.com/2021-12-04T17%3A35%3A03.4981607.jpg
    @GetMapping("pay-status")
    public ApiResponse queryOrderPayStatus(String carNum) throws AlipayApiException {
        parkingOrderService.queryOrderPayStatus(carNum);
        return ApiResponse.ofSuccess();
    }
}

