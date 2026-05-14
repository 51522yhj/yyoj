package com.yupi.yuojbackendmodel.model.dto.user;

import lombok.Data;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/2/3 19:44
 */
@Data
public class UserEditInfoRequest {
    private String userName;
    private String userProfile;
    private String userAvatar;
}
