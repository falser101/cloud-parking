package com.falser.cloud.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据dict查询dto
 *
 * @author zhangjianfei
 * @date 2021/11/27
 */
@Data
public class DataDictQueryDTO implements Serializable {

    private Long parentId;

    private String parentCode;

    private Integer status;
}
