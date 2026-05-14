package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.LikeMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CommentService;
import com.example.demo.service.LikeService;
import com.example.demo.service.QuestionFeignClient;
import com.example.demo.service.UserService;
import com.yupi.yuojbackendcommon.constant.UserConstant;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentDelRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.CommentRequest;
import com.yupi.yuojbackendmodel.model.dto.comment.LikeChangResquest;
import com.yupi.yuojbackendmodel.model.entity.Comment;
import com.yupi.yuojbackendmodel.model.entity.Like;
import com.yupi.yuojbackendmodel.model.entity.Question;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.CommentVO;
import com.yupi.yuojbackendmodel.model.vo.MyCommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author h'h
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2025-02-04 22:43:10
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private UserService userService;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Override
    public List<CommentVO> showComments(CommentRequest commentRequest, String token) {
     // User user = (User)  request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = userService.getLoginUserByToken(token);
        Integer countlimit = commentRequest.getCountlimit();
        String questionId = commentRequest.getQuestionId();
        Page<Comment> page = new Page<>(1, countlimit);
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("questionId", questionId)
                .orderByDesc("createTime");
        Page<Comment> commentPage = commentMapper.selectPage(page, commentQueryWrapper);
        return convertFromComment(commentPage.getRecords() , user);
    }

    @Override
    public Boolean likeChange(LikeChangResquest likeChangResquest, String token) {
        User user = userService.getLoginUserByToken(token);
        //User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        Like like = likeMapper.selectOne(new QueryWrapper<Like>().eq("commentId", likeChangResquest.getCommentId()).eq("userId", user.getId()));
        if (like == null) {
            like = new Like();
            like.setCommentId(Long.valueOf(likeChangResquest.getCommentId()));
            like.setUserId(user.getId());
            Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>().eq("id", likeChangResquest.getCommentId()));
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentMapper.update(comment, new QueryWrapper<Comment>().eq("id", likeChangResquest.getCommentId()));
            return likeMapper.insert(like)  > 0;
        }
        else {
            Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>().eq("id", likeChangResquest.getCommentId()));
            int likeCount = comment.getLikeCount() - 1;
            if (likeCount < 0) {
                likeCount = 0;
            }
            comment.setLikeCount(likeCount);
            commentMapper.update(comment, new QueryWrapper<Comment>().eq("id", likeChangResquest.getCommentId()));

            return likeMapper.deleteById(like.getId()) > 0;
        }


    }

    @Override
    public List<MyCommentVO> showMyComments(Integer pageSize, Long id) {
        Page<Comment> page = new Page<>(1, pageSize); // 这里假设页码从 1 开始
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<Comment>()
                .eq("userId", id)
                .orderByDesc("createTime");
        Page<Comment> commentPage = commentMapper.selectPage(page, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        List<MyCommentVO> myCommentVOList = new ArrayList<>();
        for (Comment comment : comments) {
            MyCommentVO myCommentVO = new MyCommentVO();
            String content = comment.getContent();
            myCommentVO.setContent(content);
            myCommentVO.setTime(comment.getCreateTime());
            if (comment.getQuestionId() != null)
            {
                Question questionByIdJ = questionFeignClient.getQuestionByIdJ(comment.getQuestionId());
                myCommentVO.setTitle(questionByIdJ.getTitle());
            }
            else {
                Comment comment1 = commentMapper.selectById(comment.getCommentId());
                myCommentVO.setTitle(comment1.getContent());
            }
            myCommentVO.setId(comment.getId());
            myCommentVOList.add(myCommentVO);
        }
        return myCommentVOList;
    }

    @Override
    public Integer countComments(Long id) {
        return Math.toIntExact(commentMapper.selectCount(new QueryWrapper<Comment>().eq("userId", id)));
    }

    @Override
    public Integer countCommentsByQuestionId(String questionId) {
        return Math.toIntExact(commentMapper.selectCount(new QueryWrapper<Comment>().eq("questionId", questionId)));
    }

    @Override
    public Boolean deleteComment(CommentDelRequest commentDelRequest, Long id) {
        int i = commentMapper.deleteById(commentDelRequest.getId());
        return i > 0;
    }

    private List<CommentVO> convertFromComment(List<Comment> records ,User loginUser) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }
        List<CommentVO> commentVO = new ArrayList<>();
        for (Comment record : records) {
            CommentVO commentVO1 = new CommentVO();
            commentVO1.setContent(record.getContent());
            commentVO1.setCreateTime(record.getCreateTime());
            commentVO1.setQuestionId(String.valueOf(record.getQuestionId()));
            commentVO1.setId(String.valueOf(record.getId()));
            User user = userMapper.selectById(record.getUserId());
            commentVO1.setUser(user);
            commentVO1.setLikeCount(record.getLikeCount());
            Long likeCount = likeMapper.selectCount(new QueryWrapper<Like>().eq("commentId", record.getId()).eq("userId", loginUser.getId()));
            commentVO1.setLiked(likeCount > 0);
            Long id = record.getId();
            if (id!= null) {
                List<Comment> replyList = commentMapper.selectList(new QueryWrapper<Comment>().eq("commentId", id));
                commentVO1.setReplies(convertFromComment(replyList, loginUser));
            }
            commentVO.add(commentVO1);
        }
        return commentVO;
    }
}




