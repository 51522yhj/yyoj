package com.yupi.yuojbackendmodel.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yupi.yuojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.yuojbackendmodel.model.entity.CompetitionQuestion;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: CompetitionQuestion
 * @Author: Yhj
 * @Date: 2025/2/13 16:37
 */
@Data
public class CompetitionQuestionBO {

        /**
         * 标题
         */
        private String title;

        /**
         * 内容
         */
        private String content;

    /**
     * 判题用例
     */
    private List<JudgeCase> testCases;
    private Integer score;

        private Integer memoryLimit;
      private Integer stackLimit;
      private Integer timeLimit;


        private static final long serialVersionUID = 1L;




}
