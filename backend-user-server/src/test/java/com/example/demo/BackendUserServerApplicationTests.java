package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
@Slf4j
class BackendUserServerApplicationTests {

    @Test
    void contextLoads() {
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex(("yuhaojun" + "123456789").getBytes());
        log.info("加密后的密码为：{}", encryptPassword);
    }

}
