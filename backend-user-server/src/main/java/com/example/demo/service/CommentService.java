package com.example.demo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentDelRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.LikeChangResquest;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;
import com.yupi.yuojbackendmodel.model.vo.MyCommentVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author h'h
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2025-02-04 22:43:10
*/
public interface CommentService extends IService<Comment> {

    List<CommentVO> showComments(CommentRequest commentRequest, String token);

    Boolean likeChange(LikeChangResquest likeChangResquest, String token);

    List<MyCommentVO> showMyComments(Integer pageSize, Long id);

    Integer countComments(Long id);

    Integer countCommentsByQuestionId(String questionId);

    Boolean deleteComment(CommentDelRequest commentDelRequest, Long id);
}
