package com.yyu.backendcompetitionserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuojbackendmodel.model.dto.ChartsDto;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionAddRequest;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionId;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yuojbackendmodel.model.entity.Competition;
import com.yupi.yuojbackendmodel.model.entity.CompetitionQuestion;
import com.yupi.yuojbackendmodel.model.entity.QuestionCompetitionSubmit;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.*;

import java.util.List;

/**
* @author h'h
* @description 针对表【competition(竞赛表)】的数据库操作Service
* @createDate 2025-02-13 01:09:35
*/
public interface CompetitionService extends IService<Competition> {

    Boolean publish(CompetitionAddRequest competitionAddRequest, User user);

    LoadCompetitionVO loadCompetition(Integer size);

    Boolean registerCompetition(CompetitionId competitionId, User currentUser);

    Integer loadRegisterCount(CompetitionId competitionId);

    List<MyCompetitionVO> loadCreatedCompetition(User currentUser);

    List<MyCompetitionVO> loadParticipatedCompetition(User currentUser);

    CompetitionQuestionsVO getCompetitionQuestion(CompetitionId competitionId, User currentUser);

    CompetitionQuestionDetailVO getQuestionDetail(CompetitionId competitionId);

    Boolean doSubmitCompetitionQuestion(QuestionSubmitAddRequest questionSubmitAddRequest, User currentUser);

    CompetitionQuestion getCompetitionQuestionById(long questionId);

    QuestionCompetitionSubmit getSubmitQuestionById(long questionSubmitId);

    Boolean publishCompetitionQuestion(CompetitionId competitionId, User currentUser);

    ChartsListVO getCharts(ChartsDto chartsDto);
}
