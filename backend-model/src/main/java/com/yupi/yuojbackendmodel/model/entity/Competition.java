package com.yupi.yuojbackendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 竞赛表
 * @TableName competition
 */
@TableName(value ="competition")
@Data
public class Competition implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 竞赛描述
     */
    @TableField(value = "context")
    private String context;

    /**
     * 竞赛时长
     */
    @TableField(value = "competeTime")
    private Integer competeTime;

    /**
     * 创建时间
     */
    @TableField(value = "beginTime")
    private Date beginTime;

    /**
     * 竞赛封面
     */
    @TableField(value = "userAvatar")
    private String userAvatar;

    /**
     * 创建用户 id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 是否删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;


    /**
     * 是否删除
     */
    @TableField(value = "status")
    private Integer status;
    /**
     * 总分
     */
    @TableField(value = "scores")
    private Integer scores;


    /**
     * participantLimit
     */
    @TableField(value = "participantLimit")
    private Integer participantLimit;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}