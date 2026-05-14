package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 我的评论总体返回
 * @Author: Yhj
 * @Date: 2025/2/6 22:09
 */
@Data
public class SumCommentsVO {
    private List<MyCommentVO> myCommentVOList;
    private Integer totalCount;
}
