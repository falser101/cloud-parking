package com.falser.cloud.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 数据dict级别dto
 *
 * @author zhangjianfei
 * @date 2021/11/27
 */
@Data
@ApiModel
public class DataDictLevelDTO {
    private Long id;

    @ApiModelProperty("code")
    private String dictCode;

    @ApiModelProperty("名称")
    private String dictName;

    @ApiModelProperty("父级节点id")
    private Long parentId;

    @ApiModelProperty("父级节点code")
    private String parentCode;

    @ApiModelProperty("排序号")
    private Integer sortNumber;

    @ApiModelProperty("状态;0：禁用；1：启用")
    private Integer status;

    @ApiModelProperty("备注")
    private String dictRemark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "子节点")
    private List<DataDictLevelDTO> childNodes;
}
