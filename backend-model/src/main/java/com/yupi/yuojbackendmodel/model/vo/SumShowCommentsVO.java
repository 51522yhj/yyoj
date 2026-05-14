package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/2/6 22:47
 */
@Data
public class SumShowCommentsVO {
    private List<CommentVO> commentsList;
    private Integer totalCount;
}
