package com.yyu.backendjudgeserver.judge;

import cn.hutool.json.JSONUtil;

import com.example.demo.service.CompetitionQuestionFeignClient;
import com.example.demo.service.QuestionFeignClient;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.yupi.yuojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.yuojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.yuojbackendmodel.model.entity.*;
import com.yupi.yuojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.yyu.backendjudgeserver.exception.BusinessException;
import com.yyu.backendjudgeserver.judge.codesandbox.CodeSandbox;
import com.yyu.backendjudgeserver.judge.codesandbox.CodeSandboxFactory;
import com.yyu.backendjudgeserver.judge.codesandbox.CodeSandboxProxy;
import com.yyu.backendjudgeserver.judge.strategy.JudgeContentCompetition;
import com.yyu.backendjudgeserver.judge.strategy.JudgeContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private CompetitionQuestionFeignClient competitionQuestionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionByIdJ(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("executeCodeResponse: {}", JSONUtil.toJsonStr(executeCodeResponse));
        List<String> outputList = executeCodeResponse.getOutputList();
        log.info("outputList: {}", outputList);
        log.info("outputList: {}", outputList);
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        return questionSubmitResult;
    }

    @Override
    public QuestionCompetitionSubmit doJudgeCompetition(long questionSubmitId) {
        QuestionCompetitionSubmit questionSubmit = competitionQuestionFeignClient.getSubmitQuestionById(questionSubmitId);
        log.info("questionSubmit: {}", JSONUtil.toJsonStr(questionSubmit));
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getCompetitionId();
        CompetitionQuestion competitionQuestionById = competitionQuestionFeignClient.getCompetitionQuestionById(questionId);
        log.info("competitionQuestionById: {}", competitionQuestionById);
        if (competitionQuestionById == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            log.error("题目正在判题中");
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        log.info("题目正在判题中!!!!!");
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionCompetitionSubmit questionSubmitUpdate = new QuestionCompetitionSubmit();
        questionSubmitUpdate.setCompetitionId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        log.info("questionSubmitUpdate: {}", questionSubmitUpdate);
        boolean update = competitionQuestionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        log.info("判题中、、、、、、、、");
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = competitionQuestionById.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("executeCodeResponse: {}", JSONUtil.toJsonStr(executeCodeResponse));
        List<String> outputList = executeCodeResponse.getOutputList();

        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContentCompetition judgeContext = new JudgeContentCompetition();
        log.info("judgeInfo: {}", executeCodeResponse.getJudgeInfo());
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        log.info("judgeContext.judgeInfo: {}", judgeContext.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(competitionQuestionById);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionCompetitionSubmit();
        questionSubmitUpdate.setCompetitionId(questionSubmitId);
        log.info("questionSubmitUpdate++: {}", questionSubmitUpdate);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = competitionQuestionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionCompetitionSubmit questionSubmitResult = competitionQuestionFeignClient.getSubmitQuestionById(questionId);
        return questionSubmitResult;
    }
}
