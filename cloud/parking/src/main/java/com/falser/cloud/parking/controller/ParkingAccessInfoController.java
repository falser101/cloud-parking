package com.falser.cloud.parking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.parking.entity.ParkingAccessInfo;
import com.falser.cloud.parking.service.ParkingAccessInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 出入场信息(ParkingAccessInfo)表控制层
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:33
 */
@Api(tags = "出入场信息")
@RestController
@RequestMapping("/api/parking/access_info")
public class ParkingAccessInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ParkingAccessInfoService parkingAccessInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param parkingAccessInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResponse selectAll(Page<ParkingAccessInfo> page, ParkingAccessInfo parkingAccessInfo) {
        return ApiResponse.ofSuccess(this.parkingAccessInfoService.page(page, new QueryWrapper<>(parkingAccessInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResponse selectOne(@PathVariable Serializable id) {
        return ApiResponse.ofSuccess(this.parkingAccessInfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param parkingAccessInfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ApiResponse insert(@RequestBody ParkingAccessInfo parkingAccessInfo) {
        return ApiResponse.ofSuccess(this.parkingAccessInfoService.save(parkingAccessInfo));
    }

    /**
     * 修改数据
     *
     * @param parkingAccessInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ApiResponse update(@RequestBody ParkingAccessInfo parkingAccessInfo) {
        return ApiResponse.ofSuccess(this.parkingAccessInfoService.updateById(parkingAccessInfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ApiResponse delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.parkingAccessInfoService.removeByIds(idList));
    }
}

