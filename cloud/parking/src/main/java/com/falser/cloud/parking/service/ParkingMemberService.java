package com.falser.cloud.parking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.parking.entity.ParkingMember;
import com.falser.cloud.parking.vo.ParkingMemberAddVO;
import com.falser.cloud.parking.vo.ParkingMemberUpdateVO;

/**
 * 停车场会员(ParkingMember)表服务接口
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:46
 */
public interface ParkingMemberService extends IService<ParkingMember> {

    /**
     * 添加成员
     *
     * @param vo 签证官
     */
    void addMember(ParkingMemberAddVO vo);

    /**
     * 更新的签证官
     *
     * @param vo 签证官
     */
    void updateByVO(ParkingMemberUpdateVO vo);
}

