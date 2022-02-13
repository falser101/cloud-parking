package com.falser.cloud.parking.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.falser.cloud.common.enums.StatusEnum;
import com.falser.cloud.parking.enums.UsingStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 停车位(ParkingParkingSpace)表实体类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Data
@TableName("parking_parking_space")
public class ParkingParkingSpace {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车位编号
     */
    @TableField("parking_space_num")
    private String parkingSpaceNum;

    /**
     * 楼层
     */
    @TableField("floor")
    private Integer floor;

    /**
     * 车位大小 轿车|小货车|大货车
     */
    @TableField("space_size")
    private String spaceSize;

    /**
     * 车位类型 临时停车位|租赁车位|被购买的车位
     */
    @TableField("type")
    private String type;

    /**
     * 车位备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否可用
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 使用状态 有停车|未停车
     */
    @TableField("using_status")
    private UsingStatusEnum usingStatus;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

}

