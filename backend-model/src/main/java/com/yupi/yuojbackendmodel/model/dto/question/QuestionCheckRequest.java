package com.yupi.yuojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * @Description: 管理员审核
 * @Author: Yhj
 * @Date: 2025/2/3 15:49
 */
@Data
public class QuestionCheckRequest {
    private String id;
    private Boolean pass;
}
