package com.yyu.backendcodesandboxserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.yupi.yuojbackendcommon","com.yyu.backendcodesandboxserver"})
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.example.demo.service"})
public class BackendCodeSandBoxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCodeSandBoxServerApplication.class, args);
    }

}
