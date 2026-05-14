package com.yyu.backendjudgeserver.judge;


import com.yupi.yuojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.yuojbackendmodel.model.entity.QuestionCompetitionSubmit;
import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import com.yyu.backendjudgeserver.judge.strategy.*;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        else if ("cpp".equals(language)) {
            judgeStrategy = new CppLanguageJudgeStrategy();
        }
        else if ("python".equals(language)) {
            judgeStrategy = new PythonLanguageJudgeStrategy();
        }
        else if ("c".equals(language)) {
            judgeStrategy = new CLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContentCompetition judgeContext) {
        QuestionCompetitionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        else if ("cpp".equals(language)) {
            judgeStrategy = new CppLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
