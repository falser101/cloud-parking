package com.falser.cloud.message.dto;

import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.enums.TimeCycleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageCyclePlanDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("内容")
    private String name;

    @ApiModelProperty("类型")
    private MessageCycleEnum type;

    @ApiModelProperty("时间周期")
    private TimeCycleEnum timeCycle;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("更新人")
    private Long updateBy;

}
