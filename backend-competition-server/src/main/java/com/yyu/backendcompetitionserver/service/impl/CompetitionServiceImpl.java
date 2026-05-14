package com.yyu.backendcompetitionserver.service.impl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.service.UserFeignClient;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendmodel.model.bo.CompetitionQuestionBO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuojbackendmodel.model.dto.ChartsDto;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionAddRequest;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionId;
import com.yupi.yuojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.yuojbackendmodel.model.dto.question.JudgeConfig;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.JudgeInfo;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yuojbackendmodel.model.entity.*;
import com.yupi.yuojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.yuojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yuojbackendmodel.model.vo.*;
import com.yyu.backendcompetitionserver.mapper.CompetitionMapper;
import com.yyu.backendcompetitionserver.mapper.CompetitionQuestionMapper;
import com.yyu.backendcompetitionserver.mapper.CompetitionRegisterMapper;
import com.yyu.backendcompetitionserver.mapper.QuestionCompetitionSubmitMapper;
import com.yyu.backendcompetitionserver.rabbitmq.MyMessageProducer;
import com.yyu.backendcompetitionserver.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author h'h
* @description 针对表【competition(竞赛表)】的数据库操作Service实现
* @createDate 2025-02-13 01:09:35
*/
@Service
@Slf4j
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition>
    implements CompetitionService {
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private CompetitionQuestionMapper competitionQuestionMapper;
    @Resource
    private CompetitionRegisterMapper competitionRegisterMapper;
    @Resource
    private QuestionCompetitionSubmitMapper questionCompetitionSubmitMapper;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private MyMessageProducer myMessageProducer;


    @Transactional
    @Override
    public Boolean publish(CompetitionAddRequest competitionAddRequest, User user) {
        CompetitionAddRequest competitionAddRequest1 = competitionAddRequest;
        log.info("competitionAddRequest: {}", competitionAddRequest);
    Competition competition = new Competition();

        String title = competitionAddRequest1.getTitle();
        String coverImage = competitionAddRequest1.getCoverImage();
        String description = competitionAddRequest1.getDescription();
        Integer duration = competitionAddRequest1.getDuration();
        Date startTime = competitionAddRequest1.getStartTime();
        List<CompetitionQuestionBO> questions = competitionAddRequest1.getQuestions();
        log.info("totalScore: {}", competitionAddRequest1.getTotalScore());
        competition.setScores(competitionAddRequest1.getTotalScore());
        competition.setTitle(title);
        competition.setUserAvatar(coverImage);
        competition.setContext(description);
        competition.setCompeteTime(duration);
        competition.setBeginTime(startTime);
        competition.setUserId(user.getId());
        competition.setStatus(Optional.ofNullable(user.getUserRole())
                .filter("admin"::equals)
                .isPresent() ? 0 : 1);
        competition.setParticipantLimit(competitionAddRequest1.getParticipantLimit());
        int insert = competitionMapper.insert(competition);
        log.info("competition id: {}", competition.getId());
        if (insert <= 0) {
            throw  new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        List<CompetitionQuestion> competitionQuestions = new ArrayList<>();
        for (CompetitionQuestionBO question : questions) {
            CompetitionQuestion competitionQuestion = new CompetitionQuestion();

            competitionQuestion.setTitle(question.getTitle());
            competitionQuestion.setContent(question.getContent());
            List<JudgeCase> judgeCase = question.getTestCases();
            // Object转换成json字符串
            String JsonJudgeCase = JSON.toJSONString(judgeCase);
            competitionQuestion.setJudgeCase(JsonJudgeCase);
            JudgeConfig judgeConfig = new JudgeConfig();
            judgeConfig.setMemoryLimit(Long.valueOf(question.getMemoryLimit()));
            judgeConfig.setTimeLimit(Long.valueOf(question.getTimeLimit()));
            judgeConfig.setStackLimit(Long.valueOf(question.getStackLimit()));
            competitionQuestion.setJudgeConfig(JSON.toJSONString(judgeConfig));
            competitionQuestion.setCompetitionId(competition.getId());
            competitionQuestion.setCreateTime(new Date());
            competitionQuestion.setScore(question.getScore());
            competitionQuestions.add(competitionQuestion);
        }
    competitionQuestionMapper.insertList(competitionQuestions);
        return true;
    }

    @Override
    public LoadCompetitionVO loadCompetition(Integer size) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Page<Competition> page = new Page<>(1, size);
        // 排序
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("createTime");
        Page<Competition> competitionPage = competitionMapper.selectPage(page, queryWrapper);
        List<Competition> competitions = competitionPage.getRecords();
        long total = competitionPage.getTotal();
        // List<Competition> competitions = competitionMapper.selectPage(page, null).getRecords();
       // Long l = competitionMapper.selectCount(null);
        LoadCompetitionVO loadCompetitionVO = new LoadCompetitionVO();
        loadCompetitionVO.setTotal(Math.toIntExact(total));
        List<CompetitionVO> competitionVOList = new ArrayList<>();
        for (Competition competition : competitions) {
            CompetitionVO competitionVO = new CompetitionVO();
            competitionVO.setId(competition.getId());
            competitionVO.setTitle(competition.getTitle());
            String time = dateFormat.format(competition.getBeginTime());
            competitionVO.setStartTime(time);
            competitionVO.setCoverUrl(competition.getUserAvatar());
            competitionVO.setDuration(competition.getCompeteTime());
            String createTime = dateFormat.format(competition.getCreateTime());
            competitionVO.setCreateTime(createTime);
            competitionVO.setParticipantLimit(competition.getParticipantLimit());
            //判断状态
            Date beginTime = competition.getBeginTime();
            // 直接加的是毫秒
            Date endTime = new Date(beginTime.getTime() + competition.getCompeteTime() * 60 * 1000);
         //   log.info("beginTime: {}, endTime: {}", beginTime, endTime);
            if (beginTime.before(new Date())&&endTime.after(new Date()))
            {
                competitionVO.setStatus("进行中");
            }
            else if (endTime.before(new Date()))
            {
                competitionVO.setStatus("已结束");
            }
            else {
                competitionVO.setStatus("未开始");
            }
            Long userId = competition.getUserId();
            User user = userFeignClient.getById(userId);
            competitionVO.setCreatorName(user.getUserName());
            competitionVO.setCreatorAvatar(user.getUserAvatar());
            competitionVOList.add(competitionVO);
        }
        loadCompetitionVO.setCompetitions(competitionVOList);
        return loadCompetitionVO;
    }

    @Override
    public Boolean registerCompetition(CompetitionId competitionId, User currentUser) {
        CompetitionRegister competitionRegister = new CompetitionRegister();
        competitionRegister.setCompetitionId(competitionId.getId());
        competitionRegister.setUserId(currentUser.getId());
        competitionRegister.setCreateTime(new Date());
        //判断是否已经注册
        QueryWrapper<CompetitionRegister> eq = new QueryWrapper<CompetitionRegister>().eq("competitionId", competitionId.getId()).eq("userId", currentUser.getId());

        CompetitionRegister competitionRegister1 = competitionRegisterMapper.selectOne(eq);
        if (competitionRegister1!= null) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED);
        }
        int insert = competitionRegisterMapper.insert(competitionRegister);
        if (insert <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return true;
    }

    @Override
    public Integer loadRegisterCount(CompetitionId competitionId) {
        Long l = competitionRegisterMapper.selectCount(new QueryWrapper<CompetitionRegister>().eq("competitionId", competitionId.getId()));
        return Math.toIntExact(l);
    }

    @Override
    public List<MyCompetitionVO> loadCreatedCompetition(User currentUser) {
        Long id = currentUser.getId();
        QueryWrapper<Competition> eq = new QueryWrapper<Competition>().eq("userId", id).orderByDesc("createTime");
        List<Competition> competitions = competitionMapper.selectList(eq);
        List<MyCompetitionVO> myCompetitionVOList = new ArrayList<>();
        for (Competition competition : competitions)
        {
            MyCompetitionVO myCompetitionVO = new MyCompetitionVO();
            BeanUtils.copyProperties(competition, myCompetitionVO);
            Date beginTime = competition.getBeginTime();
            // 直接加的是毫秒
            Date endTime = new Date(beginTime.getTime() + competition.getCompeteTime() * 60 * 1000);
            //   log.info("beginTime: {}, endTime: {}", beginTime, endTime);
            if (beginTime.before(new Date())&&endTime.after(new Date()))
            {
                myCompetitionVO.setStatus("进行中");
            }
            else if (endTime.before(new Date()))
            {
                myCompetitionVO.setStatus("已结束");
            }
            else {
                myCompetitionVO.setStatus("未开始");
            }
            myCompetitionVOList.add(myCompetitionVO);
        }
        return myCompetitionVOList;
       // return null;
    }

    @Override
    public List<MyCompetitionVO> loadParticipatedCompetition(User currentUser) {
        Long id = currentUser.getId();
        QueryWrapper<CompetitionRegister> eq = new QueryWrapper<CompetitionRegister>().eq("userId", id).orderByDesc("createTime");
        List<CompetitionRegister> competitionRegisters = competitionRegisterMapper.selectList(eq);
        List<Long> competitionIdList = new ArrayList<>();
        for (CompetitionRegister competitionRegister : competitionRegisters) {
            competitionIdList.add(competitionRegister.getCompetitionId());
        }
        QueryWrapper<Competition> in = new QueryWrapper<Competition>().in("id", competitionIdList).orderByDesc("createTime");
        List<Competition> competitions = competitionMapper.selectList(in);
        List<MyCompetitionVO> myCompetitionVOList = new ArrayList<>();
        for (Competition competition : competitions)
        {
            MyCompetitionVO myCompetitionVO = new MyCompetitionVO();
            BeanUtils.copyProperties(competition, myCompetitionVO);
            Date beginTime = competition.getBeginTime();
            // 直接加的是毫秒
            Date endTime = new Date(beginTime.getTime() + competition.getCompeteTime() * 60 * 1000);
            //   log.info("beginTime: {}, endTime: {}", beginTime, endTime);
            if (beginTime.before(new Date())&&endTime.after(new Date()))
            {
                myCompetitionVO.setStatus("进行中");
                CompetitionRegister competitionRegister = competitionRegisterMapper.selectOne(new QueryWrapper<CompetitionRegister>().eq("competitionId", competition.getId()).eq("userId", currentUser.getId()));
                if (competitionRegister!= null&&competitionRegister.getStatus() == 1) {
                    myCompetitionVO.setScore(competitionRegister.getScore());
                    myCompetitionVO.setStatus("已结束");
                }
            }
            else if (endTime.before(new Date()))
            {
                myCompetitionVO.setStatus("已结束");
                CompetitionRegister competitionRegister = competitionRegisterMapper.selectOne(new QueryWrapper<CompetitionRegister>().eq("competitionId", competition.getId()).eq("userId", currentUser.getId()));
                if (competitionRegister!= null) {
                    myCompetitionVO.setScore(competitionRegister.getScore());
                }
            }
            else {
                myCompetitionVO.setStatus("未开始");
            }
            myCompetitionVOList.add(myCompetitionVO);
        }
        return myCompetitionVOList;
    }

//    private Integer addScore(Competition competition, MyCompetitionVO myCompetitionVO) {
//        List<Integer> scores = new ArrayList<>();
//        Integer totalScore =0;
//        Long competitionId = competition.getId();
//        List<CompetitionQuestion> competitionQuestions = competitionQuestionMapper.selectQuestionListByCompetitionId(competitionId);
//        log.info("competitionQuestions: {}", competitionQuestions);
//        for (CompetitionQuestion competitionQuestion : competitionQuestions) {
//            Long questionId = competitionQuestion.getId();
//            QuestionCompetitionSubmit questionCompetitionSubmit = questionCompetitionSubmitMapper.selectOne(new QueryWrapper<QuestionCompetitionSubmit>().eq("competitionId", questionId));
//            if (questionCompetitionSubmit == null)
//            {
//                continue;
//            }
//            else{
//                if (questionCompetitionSubmit.getJudgeInfo().contains("Accepted"))
//                {
//                    scores.add(competitionQuestion.getScore());
//                }
//                else {
//                    scores.add(0);
//                }
//            }
//        }
//        for (Integer score : scores) {
//            totalScore+=score;
//        }
//        return totalScore;
//    }

    @Override
    public CompetitionQuestionsVO getCompetitionQuestion(CompetitionId competitionId,User currentUser) {
        Long id = competitionId.getId();
        QueryWrapper<CompetitionQuestion> eq = new QueryWrapper<CompetitionQuestion>().eq("competitionId", id);
        List<CompetitionQuestion> competitionQuestions = competitionQuestionMapper.selectList(eq);
        List<CompetitionQuestionVO> competitionQuestionVOList = new ArrayList<>();
        CompetitionQuestionsVO competitionQuestionsVO = new CompetitionQuestionsVO();
        for (CompetitionQuestion competitionQuestion : competitionQuestions) {
            CompetitionQuestionVO competitionQuestionVO = new CompetitionQuestionVO();
            competitionQuestionVO.setId(competitionQuestion.getId());
            competitionQuestionVO.setTitle(competitionQuestion.getTitle());
            competitionQuestionVO.setDescription(competitionQuestion.getContent());
            JudgeConfig judgeConfig = JSON.parseObject(competitionQuestion.getJudgeConfig(), JudgeConfig.class);
            competitionQuestionVO.setTimeLimit(Math.toIntExact(judgeConfig.getTimeLimit()));
            competitionQuestionVO.setMemoryLimit(Math.toIntExact(judgeConfig.getMemoryLimit()));
            competitionQuestionVO.setStackLimit(Math.toIntExact(judgeConfig.getStackLimit()));
            Long competitionQuestionId = competitionQuestion.getId();
            Long userId = currentUser.getId();
            QueryWrapper<QuestionCompetitionSubmit> eq1 = new QueryWrapper<QuestionCompetitionSubmit>().eq("competitionId", competitionQuestionId)
                    .eq("userId", userId).orderByDesc("createTime").last("limit 1");

            QuestionCompetitionSubmit questionCompetitionSubmit = questionCompetitionSubmitMapper.selectOne(eq1);
            log.info("questionCompetitionSubmit: {}", questionCompetitionSubmit);
            if (questionCompetitionSubmit == null)
            {
                competitionQuestionVO.setJudgeInfo("");
            }
            else{
                String judgeInfo = questionCompetitionSubmit.getJudgeInfo();
                JudgeInfo judgeInfo1 = JSON.parseObject(judgeInfo, JudgeInfo.class);
                if (StringUtils.isEmpty(judgeInfo1.getMessage()))
                {
                    competitionQuestionVO.setJudgeInfo("判题中");
                }
                else {
                    competitionQuestionVO.setJudgeInfo(judgeInfo1.getMessage());
                }
            }



            competitionQuestionVOList.add(competitionQuestionVO);

        }
        competitionQuestionsVO.setCompetitionQuestions(competitionQuestionVOList);
        Competition competition = competitionMapper.selectById(id);
        long diff = getDiff(competition);
        competitionQuestionsVO.setTimeLimit(diff);
        return competitionQuestionsVO;
    }

    @Override
    public CompetitionQuestionDetailVO getQuestionDetail(CompetitionId competitionId) {
        CompetitionQuestionDetailVO competitionQuestionDetailVO = new CompetitionQuestionDetailVO();
        Long id = competitionId.getId();
        log.info("question id: {}", id);
        QueryWrapper<CompetitionQuestion> eq = new QueryWrapper<CompetitionQuestion>().eq("id", id);
        CompetitionQuestion competitionQuestion = competitionQuestionMapper.selectOne(eq);
        competitionQuestionDetailVO.setId(competitionQuestion.getId());
        competitionQuestionDetailVO.setTitle(competitionQuestion.getTitle());
        competitionQuestionDetailVO.setContent(competitionQuestion.getContent());
        JudgeConfig judgeConfig = JSON.parseObject(competitionQuestion.getJudgeConfig(), JudgeConfig.class);
        competitionQuestionDetailVO.setTimeLimit(Math.toIntExact(judgeConfig.getTimeLimit()));
        competitionQuestionDetailVO.setMemoryLimit(Math.toIntExact(judgeConfig.getMemoryLimit()));
        competitionQuestionDetailVO.setStackLimit(Math.toIntExact(judgeConfig.getStackLimit()));
        // TODO 增加通过和总数
        Long competitionId1 = competitionQuestion.getCompetitionId();
        QueryWrapper<Competition> eq1 = new QueryWrapper<Competition>().eq("id", competitionId1);
        Competition competition = competitionMapper.selectOne(eq1);
        long diff = getDiff(competition);
        competitionQuestionDetailVO.setRemainingTime(diff);
        return competitionQuestionDetailVO;
    }

    private static long getDiff(Competition competition) {
        Date beginTime = competition.getBeginTime();
        Date now = new Date();
        if (beginTime.after(now))
        {
            throw new BusinessException(ErrorCode.COMPETITION_NOT_START);
        }
        Integer competeTime = competition.getCompeteTime();
        Date endTime = new Date(beginTime.getTime() + competeTime * 60 * 1000);
        if (endTime.before(new Date()))
        {
            throw new BusinessException(ErrorCode.COMPETITION_END);
        }
        //计算now和endTime之间的差的秒数
        long diff = (endTime.getTime() - now.getTime()) / 1000;
        return diff;
    }

    @Override
    public Boolean doSubmitCompetitionQuestion(QuestionSubmitAddRequest questionSubmitAddRequest, User currentUser) {
         String language = questionSubmitAddRequest.getLanguage();
         String code = questionSubmitAddRequest.getCode();
         Long questionId = questionSubmitAddRequest.getQuestionId();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 是否已提交题目
        long userId = currentUser.getId();
        // 每个用户串行提交题目
        QuestionCompetitionSubmit questionCompetitionSubmit = new QuestionCompetitionSubmit();
        questionCompetitionSubmit.setUserId(userId);
        questionCompetitionSubmit.setCompetitionId(questionId);
        questionCompetitionSubmit.setCode(code);
        questionCompetitionSubmit.setLanguage(language);
        questionCompetitionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionCompetitionSubmit.setJudgeInfo("{}");
        int save = questionCompetitionSubmitMapper.insert(questionCompetitionSubmit);
        if (save<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        log.info("question id: {}, user id: {}, code: {}, language: {}", questionId, userId, code, language);
        myMessageProducer.sendMessage("code_exchange1", "my_routingKey1", String.valueOf(questionId));
        return true;
    }

    @Override
    public CompetitionQuestion getCompetitionQuestionById(long questionId) {
        return competitionQuestionMapper.selectById(questionId);
    }

    @Override
    public QuestionCompetitionSubmit getSubmitQuestionById(long questionSubmitId) {
        log.info("question submit id: {}", questionSubmitId);
        QueryWrapper<QuestionCompetitionSubmit> competitionId = new QueryWrapper<QuestionCompetitionSubmit>().eq("competitionId", questionSubmitId)
                .orderByDesc("createTime").last("limit 1");
        return questionCompetitionSubmitMapper.selectOne(competitionId);
    }

    @Override
    public Boolean publishCompetitionQuestion(CompetitionId competitionId, User currentUser) {
        Long id = competitionId.getId();
        Integer totalScore = 0;
        List<CompetitionQuestion> competitionQuestions = competitionQuestionMapper.selectQuestionListByCompetitionId(id);
//       Wrapper<QuestionCompetitionSubmit> eq = new QueryWrapper<QuestionCompetitionSubmit>().eq("competitionId", id).eq("userId", currentUser.getId())
//               .orderByAsc("createTime").select("DISTINCT competitionId").last("limit "+competitionQuestions.size());
//        List<QuestionCompetitionSubmit> questionCompetitionSubmits = questionCompetitionSubmitMapper.selectList(eq);
        for (CompetitionQuestion competitionQuestion : competitionQuestions) {
            log.info("competitionQuestion: {}", competitionQuestion);
            Long questionId = competitionQuestion.getId();
            QueryWrapper<QuestionCompetitionSubmit> eq = new QueryWrapper<QuestionCompetitionSubmit>().eq("competitionId", questionId).eq("userId", currentUser.getId())
                   .orderByAsc("createTime").last("limit 1");
            QuestionCompetitionSubmit questionCompetitionSubmit = questionCompetitionSubmitMapper.selectOne(eq);
            if (questionCompetitionSubmit == null) {
                totalScore += 0;
            }
            else {
                if (questionCompetitionSubmit.getJudgeInfo().contains("Accepted")) {
                    totalScore += competitionQuestion.getScore();
                }
                else {
                    totalScore += 0;
                }
            }
        }
        CompetitionRegister competitionRegister = new CompetitionRegister();
        competitionRegister.setScore(totalScore);
        competitionRegister.setStatus(1);
        QueryWrapper<CompetitionRegister> eq = new QueryWrapper<CompetitionRegister>().eq("userId", currentUser.getId()).eq("competitionId", id);
        int update = competitionRegisterMapper.update(competitionRegister, eq);
        return update>0;
    }

    @Override
    public ChartsListVO getCharts(ChartsDto chartsDto) {
     Long id = chartsDto.getId();
     Integer size = chartsDto.getSize();
     List<ChartsVO> chartsVOList = new ArrayList<>();
     QueryWrapper<CompetitionRegister> eq = new QueryWrapper<CompetitionRegister>().eq("competitionId", id).orderByDesc("score").last("limit "+size);
        List<CompetitionRegister> competitionRegisters = competitionRegisterMapper.selectList(eq);
    for (CompetitionRegister competitionRegister : competitionRegisters) {
        ChartsVO chartsVO = new ChartsVO();
        Long userId = competitionRegister.getUserId();
        Integer score = competitionRegister.getScore();
        User byId = userFeignClient.getById(userId);
        chartsVO.setUserId(userId);
        chartsVO.setName(byId.getUserName());
        chartsVO.setAvatar(byId.getUserAvatar());
        chartsVO.setTotalScore(score);
        chartsVOList.add(chartsVO);
    }
    ChartsListVO chartsListVO = new ChartsListVO();
     chartsListVO.setCharts(chartsVOList);
     return chartsListVO;
    }
}




