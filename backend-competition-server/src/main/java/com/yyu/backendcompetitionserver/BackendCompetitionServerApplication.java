package com.yyu.backendcompetitionserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yupi.yuojbackendcommon","com.yyu.backendcompetitionserver"})
@MapperScan("com.yyu.backendcompetitionserver.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.example.demo.service"})
public class BackendCompetitionServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCompetitionServerApplication.class, args);
    }

}
