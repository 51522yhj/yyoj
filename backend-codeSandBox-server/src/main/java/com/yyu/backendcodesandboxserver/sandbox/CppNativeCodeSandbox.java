package com.yyu.backendcodesandboxserver.sandbox;

import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @Description: Cpp原生代码沙箱实现
 * @Author: Yhj
 * @Date: 2025/2/7 16:32
 */@Component
public class CppNativeCodeSandbox extends CppCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}

