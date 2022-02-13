package com.falser.cloud.parking.service;

import com.falser.cloud.parking.entity.ParkingOrder;
import com.falser.cloud.parking.vo.CarInVO;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

public interface CarIdentifyService {
    /**
     * 得到汽车num
     *
     * @param imgUrl img url
     * @return {@link String}
     */
    String getCarNum(String imgUrl) throws TencentCloudSDKException;

    /**
     * 车停在
     *
     * @param vo 签证官
     */
    void carIn(CarInVO vo);

    /**
     * 车从
     *
     * @param carNum 汽车num
     */
    ParkingOrder carOut(String carNum) throws Exception;
}
