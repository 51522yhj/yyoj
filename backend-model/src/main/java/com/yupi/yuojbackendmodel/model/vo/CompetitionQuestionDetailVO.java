package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

/**
 * @Description: 题目详情返回
 * @Author: Yhj
 * @Date: 2025/2/15 22:10
 */
@Data
public class CompetitionQuestionDetailVO {
    private Long id;
    private String title;
    private String content;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer stackLimit;
    private Integer passCondition;
    private Integer sumCondition;
    private Long remainingTime;
}
