package com.yyu.backendgateway;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.yyu.backendgateway"})
@EnableDiscoveryClient
@EnableKnife4j
@EnableFeignClients(basePackages = {"com.example.demo.service"})
public class BackendGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendGateWayApplication.class, args);
    }

}
