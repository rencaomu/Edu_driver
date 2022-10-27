package com.ren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ren.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling // 开启定时任务
public class StaAppliction {
    public static void main(String[] args) {
        SpringApplication.run(StaAppliction.class,args);
    }
}
