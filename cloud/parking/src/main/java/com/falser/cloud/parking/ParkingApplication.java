package com.falser.cloud.parking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
        "com.falser.cloud.parking",
        "com.falser.cloud.common",
})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.falser.cloud.parking.dao"})
public class ParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }

}
