package com.falser.cloud.parking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingParkingSpaceListDTO {
    private Long id;

    /**
     * 车位编号
     */
    private String parkingSpaceNum;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 车位大小 轿车|小货车|大货车
     */
    private String spaceSize;

    /**
     * 车位类型 临时停车位|租赁车位|被购买的车位
     */
    private String type;

    /**
     * 车位备注
     */
    private String remark;

    /**
     * 是否可用
     */
    private String status;

    /**
     * 使用状态 有停车|未停车
     */
    private String usingStatus;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
