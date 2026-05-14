package com.yyu.backendquesionserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【question_submit(题目提交)】的数据库操作Mapper
* @createDate 2023-08-07 20:58:53
*/
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {
  @Select("SELECT COUNT(*) AS accept_count " +
          "FROM question_submit " +
          "WHERE status = 2 " +
          "  AND JSON_EXTRACT(judgeInfo, '$.message') = 'Accepted' AND questionId = ${id};")
    Long selectAcceptCount(Long id);

  String getQuestionName(Long questionId);
  @MapKey("date")
  Map<String, Map<String, Integer>> getQuestionSubmitCountByUser(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") Long id);
}




