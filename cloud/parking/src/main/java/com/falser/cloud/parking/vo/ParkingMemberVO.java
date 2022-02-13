package com.falser.cloud.parking.vo;

import com.falser.cloud.common.enums.SexEnum;
import com.falser.cloud.parking.enums.MemberTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ParkingMemberVO {
    @NotBlank
    private String username;
    @NotBlank
    private String mobile;
    @NotNull
    private SexEnum sex;
    @NotBlank
    private String licensePlateNumber;
    @NotNull
    private MemberTypeEnum memberType;
    @NotBlank
    private String parkingSpaceNum;
    private String remark;
}
