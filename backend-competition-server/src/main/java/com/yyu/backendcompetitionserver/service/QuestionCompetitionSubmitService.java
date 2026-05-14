package com.yyu.backendcompetitionserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuojbackendmodel.model.entity.QuestionCompetitionSubmit;

/**
* @author h'h
* @description 针对表【question_competition_submit(竞赛题目提交)】的数据库操作Service
* @createDate 2025-02-15 22:02:12
*/
public interface QuestionCompetitionSubmitService extends IService<QuestionCompetitionSubmit> {

    boolean updateByCompetitionId(QuestionCompetitionSubmit questionSubmit);
}
