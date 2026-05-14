package com.yyu.backendcompetitionserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuojbackendmodel.model.entity.QuestionCompetitionSubmit;
import com.yyu.backendcompetitionserver.mapper.QuestionCompetitionSubmitMapper;
import com.yyu.backendcompetitionserver.service.QuestionCompetitionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author h'h
* @description 针对表【question_competition_submit(竞赛题目提交)】的数据库操作Service实现
* @createDate 2025-02-15 22:02:12
*/
@Service
public class QuestionCompetitionSubmitServiceImpl extends ServiceImpl<QuestionCompetitionSubmitMapper, QuestionCompetitionSubmit>
    implements QuestionCompetitionSubmitService {
    @Resource
    private QuestionCompetitionSubmitMapper questionCompetitionSubmitMapper;

    @Override
    public boolean updateByCompetitionId(QuestionCompetitionSubmit questionSubmit) {
        Long competitionId = questionSubmit.getCompetitionId();
        // 创建 QueryWrapper 并添加条件
        QueryWrapper<QuestionCompetitionSubmit> wrapper = new QueryWrapper<QuestionCompetitionSubmit>()
                .eq("competitionId", competitionId)
                .eq("isDelete", 0)
                .orderByDesc("createTime") // 按 createTime 降序排列
                .last("LIMIT 1"); // 只选择第一条记录

        int result = questionCompetitionSubmitMapper.update(questionSubmit, wrapper);
        if (result > 0) {
            return true;
        }
        return false;
    }
}




