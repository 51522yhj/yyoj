package com.yupi.yuojbackendmodel.model.vo;

import com.yupi.yuojbackendmodel.model.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 返回评论表
 * @Author: Yhj
 * @Date: 2025/2/4 22:57
 */
@Data
public class CommentVO {

    private String id;
    private String questionId;
    private String content;
    private Date createTime;
    private User user;
    private Integer likeCount;
    private Boolean liked;
    private List<CommentVO> replies;

}
