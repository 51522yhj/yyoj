package com.example.demo.service;

import com.yupi.yuojbackendmodel.model.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/2/16 22:04
 */
@FeignClient(name = "backend-competition-service", path = "/api/competition")
public interface CompetitionQuestionFeignClient {
    @GetMapping("/competition/get/id")
    CompetitionQuestion getCompetitionQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/competition/question_submit/get/id")
    QuestionCompetitionSubmit getSubmitQuestionById(@RequestParam("questionId") long questionSubmitId);
    @PostMapping("/competition/question_submit/update")
    boolean updateQuestionSubmitById(QuestionCompetitionSubmit questionSubmitUpdate);
}
