package com.falser.cloud.parking.controller;

import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.parking.service.CarIdentifyService;
import com.falser.cloud.parking.vo.CarInVO;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(tags = "车辆进出停车场")
@RestController
@RequestMapping("/api/parking/access")
public class CarIdentifyController {

    private final CarIdentifyService carIdentifyService;

    public CarIdentifyController(CarIdentifyService carIdentifyService) {
        this.carIdentifyService = carIdentifyService;
    }

    @GetMapping("identify")
    public ApiResponse imgScan(String imgUrl) throws TencentCloudSDKException {
        return ApiResponse.ofSuccess(carIdentifyService.getCarNum(imgUrl));
    }


    @ApiOperation("汽车入场接口")
    @PostMapping("in")
    public ApiResponse carIn(@Valid @NotBlank @RequestParam String carNum) {
        CarInVO carInVO = new CarInVO();
        carInVO.setCarNum(carNum);
        carIdentifyService.carIn(carInVO);
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("出场接口")
    @PostMapping("out")
    public ApiResponse carOut(@Valid @NotBlank @RequestParam String carNum) throws Exception {
        return ApiResponse.ofSuccess(carIdentifyService.carOut(carNum));
    }
}
