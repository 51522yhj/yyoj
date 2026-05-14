package com.yyu.backendcodesandboxserver.controller;


import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.yyu.backendcodesandboxserver.sandbox.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("/inner")
@Slf4j
public class MainController {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
        // 基本的认证
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        CodeSandbox codeSandbox = null;
        if (executeCodeRequest.getLanguage().equals("java")) {
             codeSandbox = new JavaNativeCodeSandbox();
        }
        else if (executeCodeRequest.getLanguage().equals("cpp")) {
            codeSandbox = new CppNativeCodeSandbox();
        }
        else if (executeCodeRequest.getLanguage().equals("python")) {
            codeSandbox = new PythonNativeCodeSandbox();
        }
        else if (executeCodeRequest.getLanguage().equals("c")) {
            codeSandbox = new CNativeCodeSandbox();
        }
log.info("InputList: " + executeCodeRequest.getInputList());
        return codeSandbox.executeCode(executeCodeRequest);
    }
}
