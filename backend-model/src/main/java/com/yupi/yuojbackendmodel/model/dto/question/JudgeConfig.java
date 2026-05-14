package com.yupi.yuojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;



    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
