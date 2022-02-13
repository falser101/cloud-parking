package com.falser.cloud.parking.mq.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingOrderDTO implements Serializable {
    @ApiParam("id")
    private Long id;
    @ApiParam("次数")
    private Integer times;
}
