package com.yyu.backendjudgeserver.judge;

import com.yupi.yuojbackendmodel.model.entity.QuestionCompetitionSubmit;
import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
    /**
     * 竞赛判题
     * @param questionSubmitId
     * @return
     */
    QuestionCompetitionSubmit doJudgeCompetition(long questionSubmitId);

//    void doCompetitionJudge(long questionSubmitId);
}
