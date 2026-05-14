package com.yyu.backendjudgeserver.controller;


import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import com.yyu.backendjudgeserver.judge.JudgeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该服务仅内部调用，不是给前端的
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController  {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId){
        System.out.println("开始判题：" + questionSubmitId);
        return judgeService.doJudge(questionSubmitId);
    }
}
