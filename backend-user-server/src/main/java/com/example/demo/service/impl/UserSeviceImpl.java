package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.GateWayFeignClient;
import com.example.demo.service.UserService;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.PasswordResetRequest;

import com.yupi.yuojbackendcommon.constant.UserConstant;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendcommon.exception.ThrowUtils;
import com.yupi.yuojbackendcommon.utils.JwtUtil;
import com.yupi.yuojbackendcommon.utils.ThreadLocalUtil;
import com.yupi.yuojbackendmodel.model.dto.user.AdminEditUserRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserEditInfoRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserQueryRequest;
import com.yupi.yuojbackendmodel.model.entity.Question;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.UserListVO;
import com.yupi.yuojbackendmodel.model.vo.UserMangeListVO;
import com.yupi.yuojbackendmodel.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @Description:
 * @Author: Yhj
 * @Date: 2025/1/10 18:34
 */
@Service
@Slf4j
public class UserSeviceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "yuhaojun";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private GateWayFeignClient gateWayFeignClient;
//    @Resource
//    private StringRedisTemplate redisTemplate;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public String userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        String key = "token:" + user.getId();
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //String account = operations.get(userAccount);
        //if (account == null) {
        //登录成功
        Map<String, Object> claims = new HashMap<>();
        /**
         * 设置id和用户名
         */
        //设置redis中存储路径

        String s = operations.get(key);
        if (StringUtils.isNotBlank(s))
        {
            //删除key路径下的所有
            stringRedisTemplate.delete(key);
        }


        claims.put("id", user.getId());
        claims.put("username", user.getUserAccount());
        String token = JwtUtil.genToken(claims);


        operations.set(key, token, 1, TimeUnit.HOURS);

        // 验证是否存入成功
        String storedToken = operations.get(key);
        if (storedToken == null) {
            log.error("Failed to store token in Redis for user ID: {}", user.getId());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Token 存储失败");
        }
        // 3. 记录用户的登录态
        log.info("user login success, userAccount: " + USER_LOGIN_STATE);
      //  request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return token;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        log.info("current user: " + currentUser);
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }


    @Override
    public Boolean resetPassword(PasswordResetRequest passwordResetRequest , String token) {
        // 1. 校验
        if (StringUtils.isAnyBlank( passwordResetRequest.getOldPassword(), passwordResetRequest.getNewPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (passwordResetRequest.getNewPassword().length() < 8 ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        Long userId = gateWayFeignClient.getUserId(token);
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String s = DigestUtils.md5DigestAsHex((SALT + passwordResetRequest.getOldPassword()).getBytes());
        if (s.equals(user.getUserPassword()))
        {
            String s1 = DigestUtils.md5DigestAsHex((SALT + passwordResetRequest.getNewPassword()).getBytes());
            user.setUserPassword(s1);
            boolean updateResult = this.updateById(user);
            if (!updateResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "密码重置失败，数据库错误");
            }
            return true;
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "旧密码错误");
        }
    }


    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(String token ) {
//        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
//            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
//        }
        // 移除登录态

        User loginUserByToken = getLoginUserByToken(token);
       // request.getSession().removeAttribute(USER_LOGIN_STATE);

        String key = "token:" + loginUserByToken.getId();
        stringRedisTemplate.delete(key);
        return true;
    }


    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public User useGetById(Long userId) {
        return this.getById(userId);
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<User> getListByIds(Set<Long> userIds) {
        return this.listByIds(userIds);
    }

    @Override
    public boolean editUser(UserEditInfoRequest editInfo,String token) {
        User user = getLoginUserByToken(token);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        user.setUserName(editInfo.getUserName());
        user.setUserProfile(editInfo.getUserProfile());
        user.setUserAvatar(editInfo.getUserAvatar());
        return userMapper.update(user, new QueryWrapper<User>().eq("id", user.getId()))>0;
    }

    @Override
    public UserMangeListVO getUserList(String token, UserQueryRequest userQueryRequest) {
//        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = getLoginUserByToken(token);
        if (!UserConstant.ADMIN_ROLE.equals(user.getUserRole())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Long id = userQueryRequest.getId();
        String account = userQueryRequest.getAccount();
        String name = userQueryRequest.getUserName();
        String role = userQueryRequest.getUserRole();
        String profile = userQueryRequest.getUserProfile();

        // 拼接查询条件
        queryWrapper.like(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.like(StringUtils.isNotBlank(account), "userAccount", account).or().like(StringUtils.isNotBlank(account), "userName", name);
//        queryWrapper.like(StringUtils.isNotBlank(name), "userName", name);
        Page<User> page = new Page<>(userQueryRequest.getCurrent(), userQueryRequest.getPageSize());

// 然后在调用分页查询方法时，将 queryWrapper 作为参数传递
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        List<UserListVO> userListVOS = new ArrayList<>();
        for (User u : userPage.getRecords()) {
            UserListVO userListVO = new UserListVO();
            String o =  stringRedisTemplate.opsForValue().get("token:" + u.getId());
            log.info("token:" + u.getId() + ":" + o);
            userListVO.setUserStatus(o != null);
            userListVO.setUserAvatar(u.getUserAvatar());
            userListVO.setUserName(u.getUserName());
            userListVO.setUserAccount(u.getUserAccount());
            userListVO.setUserRole(u.getUserRole());
            userListVO.setUserProfile(u.getUserProfile());
            userListVO.setUserId(u.getId());
            userListVO.setCreateTime(u.getCreateTime());
            userListVOS.add(userListVO);
        }
        UserMangeListVO userMangeListVO = new UserMangeListVO();
        userMangeListVO.setTotal(userPage.getTotal());
        userMangeListVO.setUserListVOList(userListVOS);
        log.info("userListVOS: " + userListVOS);
        return userMangeListVO;
    }

    @Override
    public boolean removeUser(Long userId, String token) {
        return userMapper.deleteById(userId) == 1;
    }

    @Override
    public Boolean resetPasswordForced(String userId) {
        User user = new User();
        user.setUserPassword(DigestUtils.md5DigestAsHex((SALT+"123456").getBytes()));
        return userMapper.update(user, new QueryWrapper<User>().eq("id", userId))>0;

    }

    @Override
    public User getLoginUserByToken(String token) {
        Long userId = gateWayFeignClient.getUserId(token);
        return userMapper.selectById(userId);
    }

    @Override
    public boolean adminEditUser(AdminEditUserRequest adminEditUserRequest, String token) {
        Long userId = gateWayFeignClient.getUserId(token);
        User user = userMapper.selectById(userId);
        ThrowUtils.throwIf(user == null||!UserConstant.ADMIN_ROLE.equals(user.getUserRole()), ErrorCode.NO_AUTH_ERROR);
        User user1 =new User();
        user1.setUserProfile(adminEditUserRequest.getUserProfile());
        user1.setUserName(adminEditUserRequest.getUserName());

        return userMapper.update(user1, new QueryWrapper<User>().eq("id", adminEditUserRequest.getUserId()))>0;
    }

}
