package com.yupi.yuojbackendmodel.model.dto.user;

import lombok.Data;

/**
 * @Description: 管理员编辑用户信息
 * @Author: Yhj
 * @Date: 2025/2/6 21:05
 */
@Data
public class AdminEditUserRequest {
    private String userId;
    private String userName;
    private String userProfile;
}
