package com.falser.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author sh
 * @date 2020/7/30 10:07 上午
 */
@Data
public class DataDictModifyStatusVO {
    @ApiModelProperty(value = "主键ID")
    @NotNull(message = "主键ID不能为空")
    private List<Long> ids;

    @ApiModelProperty(value = "状态，0：禁用 1：启用")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
