package com.yupi.yuojbackendmodel.model.dto.competition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yupi.yuojbackendmodel.model.bo.CompetitionQuestionBO;
import com.yupi.yuojbackendmodel.model.entity.CompetitionQuestion;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: 申请竞赛
 * @Author: Yhj
 * @Date: 2025/2/13 16:15
 */
@Data
public class CompetitionAddRequest {
    private String title;
    private String coverImage;
    private Integer participantLimit;
    private String description;
    private Integer duration ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private Integer totalScore;
    List<CompetitionQuestionBO> questions;
}
