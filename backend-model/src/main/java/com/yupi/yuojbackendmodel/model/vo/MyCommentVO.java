package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/2/6 18:28
 */
@Data
public class MyCommentVO {
    private Long id;
    private String content;
    private Date time;
    private String title;
}
