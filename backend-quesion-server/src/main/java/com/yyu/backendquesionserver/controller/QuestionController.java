package com.yyu.backendquesionserver.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.service.UserFeignClient;
import com.google.gson.Gson;
import com.yupi.yuojbackendcommon.annotation.AuthCheck;
import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendcommon.common.DeleteRequest;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.ResultUtils;
import com.yupi.yuojbackendcommon.constant.AIConstant;
import com.yupi.yuojbackendcommon.constant.UserConstant;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendcommon.exception.ThrowUtils;
import com.yupi.yuojbackendcommon.manager.AiManager;
import com.yupi.yuojbackendmodel.model.dto.question.*;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.DateRequest;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Question;
import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.QuestionSubmitVO;
import com.yupi.yuojbackendmodel.model.vo.QuestionVO;
import com.yupi.yuojbackendmodel.model.dto.question.AIQuestionRequest;
import com.yyu.backendquesionserver.mapper.QuestionSubmitMapper;
import com.yyu.backendquesionserver.service.QuestionService;
import com.yyu.backendquesionserver.service.QuestionSubmitService;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import scala.App;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;
import static com.yupi.yuojbackendcommon.manager.AiManager.STABLE_TEMPERATURE;

/**
 * 题目接口
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionSubmitMapper questionSubmitMapper;
    @Resource
    private AiManager aiManager;
    private final static Gson GSON = new Gson();

    // region 增删改查
    /**
     * 根据id获取答案
     * @param id
     * @return
     */
    @GetMapping("/get/answer")
     public BaseResponse<String> getAnswerById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(question.getAnswer());
    }

    /**
     * 创建
     *
     * @param questionAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, @RequestHeader("Authorization") String token) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        questionService.validQuestion(question, true);
        User currentUser = userFeignClient.getCurrentUser(token);

        //        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User currentUser = (User) userObj;
        question.setUserId(currentUser.getId());
        question.setFavourNum(0);
        question.setThumbNum(0);
        if (currentUser.getUserRole().equals(UserConstant.ADMIN_ROLE))
        {
            question.setStatus(1);
        }
        log.info("userRole:{}",currentUser.getUserRole());
        log.info("question:{}",question);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }
    /**
     * ai生成题目
     */
    @PostMapping("/getQuestion/ai")
    public BaseResponse<QuestionAddRequest> getQuestionByAI(@RequestBody AIQuestionRequest aiQuestionRequest, HttpServletRequest request) {
        String title = aiQuestionRequest.getTitle();
        String userMessage = getGenerateQuestionUserMessage(title);
        // 调用ai接口生成题目
        String result = aiManager.doSyncRequest(AIConstant.AUTO_CREATE_QUESTION, userMessage, null);


//        List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
//        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.USER.value(), AIConstant.AUTO_CREATE_QUESTION);
//        chatMessageList.add(systemChatMessage);
//        String result = aiManager.doRequest(chatMessageList, Boolean.FALSE, STABLE_TEMPERATURE);
        int start = result.indexOf("{");
        int end = result.lastIndexOf("}");
        String json = result.substring(start, end + 1);
        log.info("json:{}",json);
        QuestionAddRequest questionAddRequest = GSON.fromJson(json, QuestionAddRequest.class);
        return ResultUtils.success(questionAddRequest);
    }
    /**
     * 生成题目的用户消息
     */
    private String getGenerateQuestionUserMessage(String title) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(title);
        return userMessage.toString();
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param token
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, @RequestHeader("Authorization") String token) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User currentUser = (User) userObj;
        User currentUser = userFeignClient.getCurrentUser(token);
     //   User currentUser = currentUserbaseResponse.getData();
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(currentUser.getId()) && !questionService.isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/question")
    public BaseResponse<Question> getQuestionById(long id ,@RequestHeader("Authorization") String token) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        log.info("question:{}",question);
        User currentUser = userFeignClient.getCurrentUser(token);

        ThrowUtils.throwIf(!currentUser.getId().equals(question.getUserId())&&!questionService.isAdmin(currentUser), ErrorCode.NO_AUTH_ERROR);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(question);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, @RequestHeader("Authorization") String token) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        log.info("question:{}",question);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(questionService.getQuestionVO(question, token));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        Long total = questionPage.getTotal();
        questionPage.setTotal(total);
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage));
    }

    @PostMapping("/list/page/check")
    public BaseResponse<Page<QuestionVO>> listQuestionCheckByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getCheckQueryWrapper(questionQueryRequest));
        questionPage.setTotal(questionPage.getRecords().size());
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage));
    }
    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                                 @RequestHeader("Authorization") String token) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User currentUser = userFeignClient.getCurrentUser(token);

        questionQueryRequest.setUserId(currentUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage));
    }

    /**
     * 分页获取题目列表（仅管理员）
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                           @RequestHeader("Authorization") String token) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        User currentUser = userFeignClient.getCurrentUser(token);
        log.info("---***----------:{}",current);
        log.info("---***----------:{}",size);
        if (!questionService.isAdmin(currentUser)) {
            Page<Question> questionPage = questionService.page(new Page<>(current, size),
                    new QueryWrapper<Question>().eq("isDelete", 0)
                            .eq("userId", currentUser.getId()).orderByDesc("createTime")
            );
            for (Question questionVO : questionPage.getRecords()) {

                Long acceptNum = questionSubmitMapper.selectAcceptCount(questionVO.getId());
                Long submitNum =  questionSubmitMapper.selectCount(new QueryWrapper<QuestionSubmit>().eq("questionId", questionVO.getId()));
                questionVO.setSubmitNum(submitNum.intValue());
                questionVO.setAcceptedNum(acceptNum.intValue());
                log.info("acceptNum: " + acceptNum);
                log.info("submitNum: " + submitNum);
            }
            questionPage.setTotal(questionPage.getRecords().size());
            return ResultUtils.success(questionPage);
        }
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        for (Question questionVO : questionPage.getRecords()) {

            Long acceptNum = questionSubmitMapper.selectAcceptCount(questionVO.getId());
            Long submitNum =  questionSubmitMapper.selectCount(new QueryWrapper<QuestionSubmit>().eq("questionId", questionVO.getId()));
            questionVO.setSubmitNum(submitNum.intValue());
            questionVO.setAcceptedNum(acceptNum.intValue());
            log.info("acceptNum: " + acceptNum);
            log.info("submitNum: " + submitNum);
        }
        questionPage.setTotal(questionPage.getTotal());
        log.info("questionPage:{}",questionPage.getTotal());
        return ResultUtils.success(questionPage);
    }
    @PostMapping("/question/check")
    public BaseResponse<Boolean> checkQuestion(@RequestBody QuestionCheckRequest questionCheckRequest,
                                               @RequestHeader("Authorization") String token) {
        if (questionCheckRequest == null || Long.parseLong(questionCheckRequest.getId()) <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User currentUser = userFeignClient.getCurrentUser(token);
        long id = Long.parseLong(questionCheckRequest.getId());
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅管理员可审核
        if (!questionService.isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
       return ResultUtils.success(questionService.checkQuestion(id, questionCheckRequest.getPass()));
    }
    // endregion

    /**
     * 编辑（用户）
     *
     * @param questionEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, @RequestHeader("Authorization") String token) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<String> tags = questionEditRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = questionEditRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionEditRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        User currentUser =userFeignClient.getCurrentUser(token);
        //User currentUser = currentUser1.getData();

        long id = questionEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(currentUser.getId()) && !questionService.isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }
    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest

     * @return 提交记录的 id
     */
    @PostMapping("/question_submit/do")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               @RequestHeader("Authorization") String token) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    //   reementUtils.throwIf(questionSubmitAddRequest.getCode() == null || questionSubmitAddRequest.getCode().isEmpty(), ErrorCode.PARAMS_ERROR);
        User loginUser = userFeignClient.getCurrentUser(token);

        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/question_submit/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         @RequestHeader("Authorization") String token) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        User loginUser = userFeignClient.getCurrentUser(token);
        Long total =questionSubmitService.getSubmitCount();
        questionSubmitPage.setTotal(total);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
    @PostMapping("/question_submit/user/get/")
    public BaseResponse<Map<String,Integer>> getQuestionSubmitCountByUser(@RequestBody DateRequest dateRequest,
                                                            @RequestHeader("Authorization") String token) {
        User currentUser = userFeignClient.getCurrentUser(token);
        Long id = currentUser.getId();
        return ResultUtils.success(questionSubmitService.getQuestionSubmitCountByUser(dateRequest,id));
    }

}
