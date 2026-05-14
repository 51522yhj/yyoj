package com.yyu.backendcompetitionserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.yuojbackendmodel.model.entity.CompetitionQuestion;

import java.util.List;

/**
* @author h'h
* @description 针对表【competition_question(竞赛题目表)】的数据库操作Mapper
* @createDate 2025-02-13 01:09:35
* @Entity generator.domain.CompetitionQuestion
*/
public interface CompetitionQuestionMapper extends BaseMapper<CompetitionQuestion> {

    void insertList(List<CompetitionQuestion> competitionQuestions);

    List<CompetitionQuestion> selectQuestionListByCompetitionId(Long competitionId);
}




