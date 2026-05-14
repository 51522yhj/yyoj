package com.yyu.backendgateway.api;

import com.yupi.yuojbackendcommon.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 暴露给用户的ThreadLocal中存放的userId
 * @Author: Yhj
 * @Date: 2025/2/6 9:50
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class GateWayController {
    @GetMapping("/userId")
    public Long getUserId(@RequestHeader("Authorization") String token){
        log.info("Current Thread ID: {}", Thread.currentThread().getId());
        Long userId = ThreadLocalUtil.get();
        log.info("get userId from ThreadLocal: {}", userId);
        return userId;
    }
}
