package com.yupi.yuojbackendmodel.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 我的比赛
 * @Author: Yhj
 * @Date: 2025/2/14 20:57
 */
@Data
public class MyCompetitionVO {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 竞赛描述
     */
    private String context;

    /**
     * 竞赛时长
     */
    private Integer competeTime;
    /**
     * 得分
     */
    private Integer score;
    /**
     * 创建时间
     */
    private Date beginTime;

    /**
     * 竞赛封面
     */
    @TableField(value = "userAvatar")
    private String userAvatar;
    private Date createTime;

    private Integer participantLimit;




    private String status;

}
