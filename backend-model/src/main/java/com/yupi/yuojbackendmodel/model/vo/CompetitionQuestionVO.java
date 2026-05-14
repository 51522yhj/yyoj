package com.yupi.yuojbackendmodel.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 返回的题目
 * @Author: Yhj
 * @Date: 2025/2/15 1:01
 */
@Data
public class CompetitionQuestionVO {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String description;

    /**
     * 判题用例（json 数组）
     */
    private Integer timeLimit;


    private Integer memoryLimit;

    private Integer stackLimit;

    private String JudgeInfo;


}
