package com.falser.cloud.parking.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CarInVO {
    @ApiModelProperty("车牌号")
    @NotBlank
    private String carNum;

    private String test;
}
