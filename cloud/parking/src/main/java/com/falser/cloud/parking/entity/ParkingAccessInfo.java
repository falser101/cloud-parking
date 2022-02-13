package com.falser.cloud.parking.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.falser.cloud.parking.enums.ParkingTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 出入场信息(ParkingAccessInfo)表实体类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:36
 */
@Data
@TableName("parking_access_info")
public class ParkingAccessInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入场时间
     */
    @TableField("entry_time")
    private LocalDateTime entryTime;

    /**
     * 出场时间
     */
    @TableField("out_time")
    private LocalDateTime outTime;

    /**
     * 车牌号
     */
    @TableField("license_plate_number")
    private String licensePlateNumber;

    /**
     * 入场图像
     */
    @TableField("enter_image")
    private String enterImage;

    /**
     * 出场图像
     */
    @TableField("his_image")
    private String hisImage;

    /**
     * 停车类型 临时停车temporary|租车位停车Renting|购买车位停车purchase
     */
    @TableField("type")
    private ParkingTypeEnum type;

    /**
     * 车位编号
     */
    @TableField("parking_space_num")
    private String parkingSpaceNum;

    /**
     * 删除标志 0删除 1未删除
     */
    @TableField("del_flag")
    private String delFlag;

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

