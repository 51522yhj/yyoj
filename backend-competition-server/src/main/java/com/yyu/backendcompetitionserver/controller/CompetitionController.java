package com.yyu.backendcompetitionserver.controller;

import com.example.demo.service.UserFeignClient;
import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendcommon.common.ResultUtils;
import com.yupi.yuojbackendmodel.model.dto.ChartsDto;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionAddRequest;
import com.yupi.yuojbackendmodel.model.dto.competition.CompetitionId;
import com.yupi.yuojbackendmodel.model.dto.competition.SizeRequest;
import com.yupi.yuojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yuojbackendmodel.model.entity.*;
import com.yupi.yuojbackendmodel.model.vo.*;
import com.yyu.backendcompetitionserver.service.CompetitionService;
import com.yyu.backendcompetitionserver.service.QuestionCompetitionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 竞赛管理
 * @Author: Yhj
 * @Date: 2025/2/13 10:19
 */
@RestController
@RequestMapping("/competition")
@Slf4j
public class CompetitionController {
    @Resource
    private CompetitionService competitionService;
    @Resource
    private QuestionCompetitionSubmitService questionCompetitionSubmitService;
    @Resource
    private UserFeignClient userFeignClient;

    @GetMapping("/get/id")
    CompetitionQuestion getCompetitionQuestionById(@RequestParam("questionId") long questionId){
        return competitionService.getCompetitionQuestionById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    QuestionCompetitionSubmit getSubmitQuestionById(@RequestParam("questionId") long questionSubmitId){
        return competitionService.getSubmitQuestionById(questionSubmitId);
    }
    /**
     * 发布竞赛
     */
    @PostMapping("/publish")
    public BaseResponse<Boolean> publish(@RequestBody  CompetitionAddRequest competitionAddRequest, HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)
        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.publish(competitionAddRequest,currentUser));
    }
    /**
     * 加载竞赛表
     */
    @PostMapping("/loadCompetition")
    public BaseResponse<LoadCompetitionVO> loadCompetition(@RequestBody SizeRequest sizeRequest , HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)
        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.loadCompetition(sizeRequest.getSize()));
      //  return ResultUtils.success(competitionService.publish(competitionAddRequest,currentUser));
    }
    /**
     * 加载竞赛表
     */
    @PostMapping("/register")
    public BaseResponse<Boolean> registerCompetition(@RequestBody CompetitionId competitionId , HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)
        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.registerCompetition(competitionId,currentUser));
        //  return ResultUtils.success(competitionService.publish(competitionAddRequest,currentUser));
    }
    /**
     * 加载该竞赛参与的人数
     */
    @PostMapping("/loadRegisterCount")
    public BaseResponse<Integer> loadRegisterCount(@RequestBody CompetitionId competitionId , HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)
        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.loadRegisterCount(competitionId));
        //  return ResultUtils.success(competitionService.publish(competitionAddRequest,currentUser));
    }

    /**
     * 加载用户创建的比赛
     */
    @PostMapping("/loadCreatedCompetition")
    public BaseResponse<List<MyCompetitionVO>> loadCreatedCompetition(HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)
        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.loadCreatedCompetition(currentUser));
        //  return ResultUtils.success(competitionService.publish(competitionAddRequest,currentUser));
    }
    /**
     * 加载用户参与的比赛
     */
    @PostMapping("/loadParticipatedCompetition")
    public BaseResponse<List<MyCompetitionVO>> loadParticipatedCompetition(HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        if (currentUser == null)

        {
            return ResultUtils.error(401, "请先登录");
        }
        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.loadParticipatedCompetition(currentUser));
    }
    /**
     * 获取比赛id下的题目
     */
    @PostMapping("/getCompetitionQuestion")
    public BaseResponse<CompetitionQuestionsVO> getCompetitionQuestion(@RequestBody CompetitionId competitionId,HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
//        User currentUser = tokenUtil.getUser(request);
//        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.getCompetitionQuestion(competitionId,currentUser));
    }

    /**
     * 提交比赛id下的所有题目
     */
    @PostMapping("/publishCompetitionQuestion")
    public BaseResponse<Boolean> publishCompetitionQuestion(@RequestBody CompetitionId competitionId,HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
//        User currentUser = tokenUtil.getUser(request);
//        log.info("当前用户：{}", currentUser.toString());
        return ResultUtils.success(competitionService.publishCompetitionQuestion(competitionId,currentUser));
    }
    /**
     * 获取题目id下的题目详情
     */
    @PostMapping("/getQuestionDetail")
    public BaseResponse<CompetitionQuestionDetailVO> getQuestionDetail(@RequestBody CompetitionId competitionId) {

        return ResultUtils.success(competitionService.getQuestionDetail(competitionId));
    }

    /**
     * 获取题目id下的题目详情
     */
    @PostMapping("/doSubmitCompetitionQuestion")
    public BaseResponse<Boolean> doSubmitCompetitionQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        //获取请求头参数
        String token = request.getHeader("Authorization");
        User currentUser = userFeignClient.getCurrentUser(token);
        return ResultUtils.success(competitionService.doSubmitCompetitionQuestion(questionSubmitAddRequest,currentUser));
    }

    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionCompetitionSubmit questionSubmit) {
        log.info("updateQuestionSubmitById:{}", questionSubmit);
        return questionCompetitionSubmitService.updateByCompetitionId(questionSubmit);
    }
    @PostMapping("/get/charts")
    public BaseResponse<ChartsListVO> getCharts(@RequestBody ChartsDto chartsDto) {
        log.info("chartsDto:{}", chartsDto);
        return ResultUtils.success(competitionService.getCharts(chartsDto));
    }
//    @GetMapping("/question_submit/get/id")
//    QuestionCompetitionSubmit getQuestionSubmitById(Long questionId){
//        return questionCompetitionSubmitService.getById(questionId);
//    }
}
