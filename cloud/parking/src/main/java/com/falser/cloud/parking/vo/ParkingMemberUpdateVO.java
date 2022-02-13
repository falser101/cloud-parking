package com.falser.cloud.parking.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingMemberUpdateVO extends ParkingMemberVO {
    @NotNull
    private Long id;
}
