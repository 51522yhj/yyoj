package com.yyu.backendcodesandboxserver;

import com.yupi.yuojbackendcommon.utils.ProcessUtils;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteMessage;
import com.yyu.backendcodesandboxserver.sandbox.CppCodeSandboxTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BackendCodeSandBoxServerApplicationTests {

    @Autowired
    private CppCodeSandboxTemplate cppCodeSandboxTemplate;
    @Test
    void contextLoads() throws IOException {
        System.out.println("Hello World");
        String path ="D:\\code\\hhao\\target\\main 1 1";
        Process runProcess = Runtime.getRuntime().exec(path);
        ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
        System.out.println(executeMessage);
      //  System.out.println(response);
    }

}
