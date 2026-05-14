package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import com.example.demo.service.GateWayFeignClient;
import com.example.demo.service.UserService;
import com.yupi.yuojbackendcommon.common.BaseResponse;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.common.PasswordResetRequest;
import com.yupi.yuojbackendcommon.common.ResultUtils;

import com.yupi.yuojbackendcommon.constant.FileConstant;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendcommon.manager.CosManager;
import com.yupi.yuojbackendmodel.model.dto.file.UploadFileRequest;
import com.yupi.yuojbackendmodel.model.dto.user.*;
import com.yupi.yuojbackendmodel.model.enums.FileUploadBizEnum;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.UserMangeListVO;
import com.yupi.yuojbackendmodel.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.yupi.yuojbackendmodel.model.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private GateWayFeignClient gateWayFeignClient;
    @GetMapping("/hello")
    public String hello(@RequestHeader("Authorization") String token) {
        return "hello" + gateWayFeignClient.getUserId(token) ;
    }
    @Resource
    private UserService userService;
    // region 登录相关
    @Resource
    private CosManager cosManager;
    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
    @PostMapping("/resetPassword")
    public BaseResponse<Boolean> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest , @RequestHeader("Authorization") String token) {
        if (passwordResetRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        else {
            return ResultUtils.success(userService.resetPassword(passwordResetRequest,token));
        }
    }
    /**
     * 编辑用户信息
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editUser(@RequestBody UserEditInfoRequest editInfo,@RequestHeader("Authorization") String token) {
        if (editInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.editUser(editInfo ,token);
        return ResultUtils.success(result);
    }
    /**
     * 管理员编辑用户信息
     */
    @PostMapping("/admin/edit")
    public BaseResponse<Boolean> adminEditUser(@RequestBody AdminEditUserRequest adminEditUserRequest, @RequestHeader("Authorization") String token) {
        boolean result = userService.adminEditUser(adminEditUserRequest ,token);
        return ResultUtils.success(result);
    }
    /**
     * 删除用户信息
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestParam("userId") Long userId,@RequestHeader("Authorization") String token) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeUser(userId ,token);
        return ResultUtils.success(result);
    }
    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        String token = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(token);
    }

    /**
     * 用户注销
     *
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        boolean result = userService.userLogout(token);
         request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param token
     * @return
     */
//    @GetMapping("/get/login")
//    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
//        User user = userService.getLoginUser(request);
//        try {
//            return ResultUtils.success(userService.getLoginUserVO(user));
//        }catch (BusinessException e) {
//            return ResultUtils.error(e.getCode(),e.getMessage());
//        }
//
//    }
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(@RequestHeader("Authorization") String token) {
//        User user = userService.getLoginUser(request);
        User user = userService.getLoginUserByToken(token);
        try {
            return ResultUtils.success(userService.getLoginUserVO(user));
        }catch (BusinessException e) {
            return ResultUtils.error(e.getCode(),e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     * @param token
     * @return
     */
    @GetMapping("/get/user")
    public User getCurrentUser(@RequestHeader("Authorization") String token) {
        return userService.getLoginUserByToken(token);
    }
    @GetMapping("/get/byId")
    public User getById(Long userId) {
        return userService.useGetById(userId);
    }
    @PostMapping("/get/userVo")
    public UserVO getUserVO(@RequestBody  User user) {
        return userService.getUserVO(user);
    }
    @GetMapping("/get/listByIds")
    public List<User> listByIds(@RequestParam("userIds")Set<Long> userIds) {
        return userService.getListByIds(userIds);
    }
    @PostMapping("/get/userList")
    public BaseResponse<UserMangeListVO> getUserList(@RequestHeader("Authorization") String token, @RequestBody UserQueryRequest userQueryRequest) {
//        return userService.getListByIds(userIds);
        return ResultUtils.success(userService.getUserList(token,userQueryRequest));
    }
    @PostMapping("/resetPasswordForced")
    public BaseResponse<Boolean> resetPasswordForced(String userId) {
        return ResultUtils.success(userService.resetPasswordForced(userId));
    }


    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadFileRequest
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                           UploadFileRequest uploadFileRequest, HttpServletRequest request) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        String authorization = request.getHeader("Authorization");
        User loginUser = userService.getLoginUserByToken(authorization);
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            // 返回可访问地址
            return ResultUtils.success(FileConstant.COS_HOST + filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }

}
