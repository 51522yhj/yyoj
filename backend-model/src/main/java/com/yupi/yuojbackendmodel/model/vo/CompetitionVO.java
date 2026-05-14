package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 竞赛细节
 * @Author: Yhj
 * @Date: 2025/2/14 0:49
 */
@Data
public class CompetitionVO {
    private Long id;
    private String title;
    private String startTime;
    private String coverUrl;
    private Integer duration;
    private Integer participantLimit;
    private String status;
    private String creatorName;
    private String creatorAvatar;
    private String createTime;
}
