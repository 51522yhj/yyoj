package com.example.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description: 获取userId
 * @Author: Yhj
 * @Date: 2025/2/6 13:48
 */
@FeignClient(name = "gateway", url = "http://localhost:8101")
public interface GateWayFeignClient {
    @GetMapping("/gateway/userId")
    Long getUserId(@RequestHeader("Authorization") String token) ;
}
