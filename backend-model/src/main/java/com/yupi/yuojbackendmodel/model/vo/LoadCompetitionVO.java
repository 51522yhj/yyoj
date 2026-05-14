package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 竞赛表
 * @Author: Yhj
 * @Date: 2025/2/14 0:48
 */
@Data
public class LoadCompetitionVO {

   private List<CompetitionVO> competitions;
   private Integer total;
}
