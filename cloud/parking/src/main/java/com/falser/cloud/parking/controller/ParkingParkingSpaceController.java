package com.falser.cloud.parking.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.dto.UserInfoDTO;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.parking.entity.ParkingParkingSpace;
import com.falser.cloud.parking.service.ParkingParkingSpaceService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 停车位(ParkingParkingSpace)表控制层
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Api(tags = "停车位")
@RestController
@RequestMapping("/api/parking/parking_space")
public class ParkingParkingSpaceController {
    /**
     * 服务对象
     */
    @Resource
    private ParkingParkingSpaceService parkingParkingSpaceService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param parkingParkingSpace 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResponse selectAll(Page<ParkingParkingSpace> page, ParkingParkingSpace parkingParkingSpace) {
        return ApiResponse.ofSuccess(this.parkingParkingSpaceService.page(page, new QueryWrapper<>(parkingParkingSpace)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResponse selectOne(@PathVariable Serializable id) {
        return ApiResponse.ofSuccess(this.parkingParkingSpaceService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param parkingParkingSpace 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResponse insert(@RequestBody ParkingParkingSpace parkingParkingSpace) {
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        parkingParkingSpace.setCreateBy(userinfo.getUserInfo().getUsername());
        return ApiResponse.ofSuccess(this.parkingParkingSpaceService.save(parkingParkingSpace));
    }

    /**
     * 修改数据
     *
     * @param parkingParkingSpace 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ApiResponse update(@RequestBody ParkingParkingSpace parkingParkingSpace) {
        UserInfoDTO userinfo = (UserInfoDTO) StpUtil.getSession().get("userinfo");
        parkingParkingSpace.setUpdateBy(userinfo.getUserInfo().getUsername());
        return ApiResponse.ofSuccess(this.parkingParkingSpaceService.updateById(parkingParkingSpace));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResponse delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.parkingParkingSpaceService.removeByIds(idList));
    }
}

