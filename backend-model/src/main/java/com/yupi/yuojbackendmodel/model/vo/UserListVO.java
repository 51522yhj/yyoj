package com.yupi.yuojbackendmodel.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 用户管理列表
 * @Author: Yhj
 * @Date: 2025/2/4 15:41
 */
@Data
public class UserListVO {
    private Long userId;
    private String userAvatar;
    private String userName;
    private String userAccount;
    private String userProfile;
    private String userRole;
    private Boolean userStatus;
    /**
     * 创建时间
     */
    private Date createTime;
}
