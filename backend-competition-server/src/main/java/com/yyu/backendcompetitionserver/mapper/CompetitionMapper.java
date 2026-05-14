package com.yyu.backendcompetitionserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.yuojbackendmodel.model.entity.Competition;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
* @author h'h
* @description 针对表【competition(竞赛表)】的数据库操作Mapper
* @createDate 2025-02-13 01:09:35
* @Entity generator.domain.Competition
*/
public interface CompetitionMapper extends BaseMapper<Competition> {


        @Insert("INSERT INTO competition (title,userId, userAvatar, context, competeTime, beginTime, status,participantLimit,scores) " +
                "VALUES (#{title},#{userId}, #{userAvatar}, #{context}, #{competeTime}, #{beginTime}, #{status},#{participantLimit},#{scores})")
        @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
        int insert(Competition competition);

}




