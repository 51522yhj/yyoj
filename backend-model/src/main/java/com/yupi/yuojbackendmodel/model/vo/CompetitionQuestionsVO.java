package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 返回题目信息VO
 * @Author: Yhj
 * @Date: 2025/3/6 16:41
 */
@Data
public class CompetitionQuestionsVO {
    private List<CompetitionQuestionVO> competitionQuestions;
    private Long timeLimit;
}
