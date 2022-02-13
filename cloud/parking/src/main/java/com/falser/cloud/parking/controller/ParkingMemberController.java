package com.falser.cloud.parking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.parking.entity.ParkingMember;
import com.falser.cloud.parking.service.ParkingMemberService;
import com.falser.cloud.parking.vo.ParkingMemberAddVO;
import com.falser.cloud.parking.vo.ParkingMemberUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 停车场会员(ParkingMember)表控制层
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Api(tags = "停车场会员")
@RestController
@RequestMapping("/api/parking/member")
public class ParkingMemberController {
    /**
     * 服务对象
     */
    @Resource
    private ParkingMemberService parkingMemberService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param parkingMember 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ApiResponse<IPage<ParkingMember>> selectAll(Page<ParkingMember> page, ParkingMember parkingMember) {
        return ApiResponse.ofSuccess(this.parkingMemberService.page(page, new QueryWrapper<>(parkingMember)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ApiResponse<ParkingMember> selectOne(@PathVariable Serializable id) {
        return ApiResponse.ofSuccess(this.parkingMemberService.getById(id));
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping
    public ApiResponse<String> insert(@RequestBody ParkingMemberAddVO vo) {
        this.parkingMemberService.addMember(vo);
        return ApiResponse.ofSuccess();
    }

    /**
     * 修改数据
     *
     * @param parkingMember 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改")
    public ApiResponse<Boolean> update(@RequestBody ParkingMemberUpdateVO vo) {
        this.parkingMemberService.updateByVO(vo);
        return ApiResponse.ofSuccess();
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除")
    public ApiResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ApiResponse.ofSuccess(this.parkingMemberService.removeByIds(idList));
    }
}

