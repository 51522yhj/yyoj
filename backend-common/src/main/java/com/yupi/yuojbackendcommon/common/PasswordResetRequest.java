package com.yupi.yuojbackendcommon.common;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String oldPassword;
    private String newPassword;
}
