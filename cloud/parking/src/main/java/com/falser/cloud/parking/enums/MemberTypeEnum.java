package com.falser.cloud.parking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberTypeEnum {
    TEMPORARY(ParkingTypeEnum.TEMPORARY),
    TOP_UP(ParkingTypeEnum.TOP_UP),
    REGULAR(ParkingTypeEnum.RENTING),
    PURCHASED(ParkingTypeEnum.PURCHASE);

    private final ParkingTypeEnum parkingTypeEnum;
}
