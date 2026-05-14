package com.yupi.yuojbackendmodel.model.dto.comment;

import lombok.Data;

/**
 * @Description: 回复
 * @Author: Yhj
 * @Date: 2025/2/4 23:06
 */
@Data
public class CommentAddRequest {
    private String content;
    private String questionId;
    private String commentId;

}
