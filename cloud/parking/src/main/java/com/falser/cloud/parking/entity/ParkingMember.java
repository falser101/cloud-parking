package com.falser.cloud.parking.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.falser.cloud.common.enums.SexEnum;
import com.falser.cloud.parking.enums.MemberTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 停车场会员(ParkingMember)表实体类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
@Data
@TableName("parking_member")
public class ParkingMember {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员名
     */
    @TableField("username")
    private String username;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 车牌号
     */
    @TableField("license_plate_number")
    private String licensePlateNumber;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 会员类型 临时会员|普通会员|超级会员|已购买车位会员
     */
    @TableField("member_type")
    private MemberTypeEnum memberType;

    /**
     * 绑定的车位号
     */
    @TableField("parking_space_num")
    private String parkingSpaceNum;

    /**
     * 性别
     */
    @TableField("sex")
    private SexEnum sex;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 激活时间
     */
    @TableField("active_time")
    private LocalDateTime activeTime;

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

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}

