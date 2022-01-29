package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class DataDictQueryVO implements Serializable {

    @ApiParam("父级字典code, 为空查询第一级")
    private String parentCode;

    @ApiParam("状态;0：禁用；1：启用")
    private Integer status;
}
