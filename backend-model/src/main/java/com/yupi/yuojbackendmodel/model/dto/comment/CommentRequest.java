package com.yupi.yuojbackendmodel.model.dto.comment;

import lombok.Data;

/**
 * @Description: 请求题目的评论信息
 * @Author: Yhj
 * @Date: 2025/2/4 22:55
 */
@Data
public class CommentRequest {
    private String questionId;
    private Integer countlimit;
}
