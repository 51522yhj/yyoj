package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionFeignClient;
import com.example.demo.service.UserService;
import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.ResultUtils;
import com.yupi.yuojbackendmodel.model.dto.comment.*;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.entity.Question;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;
import com.yupi.yuojbackendmodel.model.vo.MyCommentVO;
import com.yupi.yuojbackendmodel.model.vo.SumCommentsVO;
import com.yupi.yuojbackendmodel.model.vo.SumShowCommentsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @Description: 评论管理
 * @Author: Yhj
 * @Date: 2025/2/4 22:40
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @PostMapping("/likeChange")
    public BaseResponse<Boolean> likeChange(@RequestBody LikeChangResquest likeChangResquest,@RequestHeader("Authorization") String token) {


        return ResultUtils.success(commentService.likeChange(likeChangResquest, token));
    }

    @PostMapping("/showComments")
    public BaseResponse<SumShowCommentsVO> showComments(@RequestBody CommentRequest commentRequest, @RequestHeader("Authorization") String token) {
        log.info("showComments: " + commentRequest.toString());
        List<CommentVO> commentVO = commentService.showComments(commentRequest, token);
        SumShowCommentsVO sumShowCommentsVO = new SumShowCommentsVO();
        sumShowCommentsVO.setCommentsList(commentVO);
        sumShowCommentsVO.setTotalCount(commentService.countCommentsByQuestionId(commentRequest.getQuestionId()));
        return ResultUtils.success(sumShowCommentsVO);
    }
    @PostMapping("/showMyComments")
    public BaseResponse<SumCommentsVO> showMyComments(@RequestBody MyCommentRequest myCommentRequest, @RequestHeader("Authorization") String token) {
        User loginUser = userService.getLoginUserByToken(token);
        List<MyCommentVO> myCommentVOS = commentService.showMyComments(myCommentRequest.getPageSize(), loginUser.getId());
        SumCommentsVO sumCommentsVO = new SumCommentsVO();
        sumCommentsVO.setMyCommentVOList(myCommentVOS);
        //统计总评论数
        Integer countComments = commentService.countComments(loginUser.getId());
        sumCommentsVO.setTotalCount(countComments);
        return ResultUtils.success(sumCommentsVO);
       // return ResultUtils.success(commentVO);
    }
    @PostMapping("/deleteComment")
    public BaseResponse<Boolean> deleteComment(HttpServletRequest request, @RequestBody CommentDelRequest commentDelRequest) {
        String token = request.getHeader("Authorization");
        User loginUser = userService.getLoginUserByToken(token);

        return ResultUtils.success(commentService.deleteComment(commentDelRequest, loginUser.getId()));
        // return ResultUtils.success(commentVO);
    }
    @PostMapping("/addComment")
    public BaseResponse addComment(@RequestBody CommentAddRequest commentAddRequest, @RequestHeader("Authorization") String token) {
        User user = userService.getLoginUserByToken(token);
       // User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        log.info("addComment: " + commentAddRequest.toString());
        Comment comment = new Comment();
        comment.setUserId(user.getId());
        if (StringUtils.isBlank(commentAddRequest.getQuestionId()))
        {
            Comment byId = commentService.getById(commentAddRequest.getCommentId());
            if (byId == null)
            {
                return ResultUtils.error(ErrorCode.TOKEN_ERROR);

            }
            comment.setCommentId(Long.valueOf(commentAddRequest.getCommentId()));

        }
        else {
            Question questionByIdJ = questionFeignClient.getQuestionByIdJ(Long.parseLong(commentAddRequest.getQuestionId()));
            if (questionByIdJ == null) {
                return ResultUtils.error(ErrorCode.QUESTION_NOT_EXIST);
            }
            comment.setQuestionId(Long.valueOf(commentAddRequest.getQuestionId()));
        }
        comment.setContent(commentAddRequest.getContent());
        comment.setCreateTime(new Date());
        comment.setLikeCount(0);

        return ResultUtils.success(commentService.save(comment));
    }
}
