package com.falser.cloud.parking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.parking.dao.ParkingAccessInfoDao;
import com.falser.cloud.parking.entity.ParkingAccessInfo;
import com.falser.cloud.parking.service.ParkingAccessInfoService;
import org.springframework.stereotype.Service;

/**
 * 出入场信息(ParkingAccessInfo)表服务实现类
 *
 * @author zhangjianfei
 * @since 2021-11-30 14:13:44
 */
@Service
public class ParkingAccessInfoServiceImpl extends ServiceImpl<ParkingAccessInfoDao, ParkingAccessInfo> implements ParkingAccessInfoService {

}

