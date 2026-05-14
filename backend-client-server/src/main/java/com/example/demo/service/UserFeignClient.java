package com.example.demo.service;

import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@FeignClient(name = "backend-user-server", path = "/api/user")
public interface UserFeignClient {
    @GetMapping("/user/get/byId")
     User getById(@RequestParam("userId") Long userId);
    @PostMapping("/user/get/userVo")
    UserVO getUserVO(@RequestBody  User user);
    @GetMapping("/user/get/listByIds")
    List<User> listByIds(@RequestParam("userIds") Set<Long> userIds);
    @GetMapping("/user/get/user")
    User getCurrentUser(@RequestHeader("Authorization") String token);
}
