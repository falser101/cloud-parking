package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author sh
 * @date 2020/7/23 5:29 下午
 */
@Data
@ApiModel
public class DataDictSaveVO {
    @ApiModelProperty(value = "字典码")
    @NotBlank(message = "字典码补不能为空")
    private String dictCode;

    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "父code码")
    private String parentCode;

    @ApiModelProperty(value = "字典备注")
    private String dictRemark;
}
