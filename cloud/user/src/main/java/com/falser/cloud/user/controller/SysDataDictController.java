package com.falser.cloud.user.controller;


import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.user.dto.DataDictDTO;
import com.falser.cloud.user.dto.DataDictLevelDTO;
import com.falser.cloud.user.service.SysDataDictService;
import com.falser.cloud.user.vo.DataDictModifyVO;
import com.falser.cloud.user.vo.DataDictQueryVO;
import com.falser.cloud.user.vo.DataDictSaveVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据字典表(SysDataDict)表控制层
 *
 * @author falser
 * @since 2022-02-13 11:30:52
 */
@RestController
@RequestMapping("/api/data-dict")
public class SysDataDictController {
    private final SysDataDictService sysDataDictService;

    public SysDataDictController(@Lazy SysDataDictService sysDataDictService) {
        this.sysDataDictService = sysDataDictService;
    }

    @ApiOperation(value = "多级数据字典列表")
    @GetMapping
    public ApiResponse<List<DataDictLevelDTO>> selectDataDictList(DataDictQueryVO vo) {
        return ApiResponse.ofSuccess(sysDataDictService.findLevel(vo));
    }

    @ApiOperation(value = "通过id查询")
    @GetMapping("{id}")
    public ApiResponse<DataDictDTO> getDataDict(@PathVariable Long id) {
        return ApiResponse.ofSuccess(sysDataDictService.getDataDict(id));
    }

    @ApiOperation(value = "新增数据字典")
    @PostMapping
    public ApiResponse<String> saveDataDict(@Valid @RequestBody DataDictSaveVO vo) {
        sysDataDictService.saveDataDict(vo);
        return ApiResponse.ofSuccess();
    }

    @ApiOperation(value = "修改数据字典")
    @PutMapping
    public ApiResponse<String> modifyDataDict(@Valid @RequestBody DataDictModifyVO vo) {
        sysDataDictService.modifyDataDict(vo);
        return ApiResponse.ofSuccess();
    }
}

