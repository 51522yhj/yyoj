package com.example.demo.service;

import com.yupi.yuojbackendcommon.common.PasswordResetRequest;
import com.yupi.yuojbackendmodel.model.dto.user.AdminEditUserRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserEditInfoRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.UserListVO;
import com.yupi.yuojbackendmodel.model.vo.UserMangeListVO;
import com.yupi.yuojbackendmodel.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/1/10 2:16
 */
public interface UserService {
    long userRegister(String userAccount, String userPassword, String checkPassword);

    Boolean resetPassword(PasswordResetRequest passwordResetRequest,String token);

    String userLogin(String userAccount, String userPassword, HttpServletRequest request);

    boolean userLogout(String token);

    User getLoginUser(HttpServletRequest request);

    LoginUserVO getLoginUserVO(User user);

    User useGetById(Long userId);

    UserVO getUserVO(User user);

    List<User> getListByIds(Set<Long> userIds);

    boolean editUser(UserEditInfoRequest editInfo, String token);

    UserMangeListVO getUserList(String token, UserQueryRequest userQueryRequest);

    boolean removeUser(Long userId, String token);

    Boolean resetPasswordForced(String userId);

    User getLoginUserByToken(String token);

    boolean adminEditUser(AdminEditUserRequest adminEditUserRequest, String token);
}
