package com.yupi.yuojbackendcommon.common;

/**
 * 自定义错误码
 *
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    ALREADY_REGISTERED(40102, "已报名不需重复报名"),
    COMPETITION_NOT_START(40103, "比赛未开始"),
    COMPETITION_END(40104, "比赛已结束"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    API_REQUEST_ERROR(50010, "接口调用失败"),
    COMPILE_ERROR(50011, "Compile Error"),
    TIMEOUT_ERROR(50012, "Timeout Error"),
    RUN_ERROR(50013, "Wrong Answer"),
    COMMENT_NOT_EXIST(60001, "评论不存在"),
    QUESTION_NOT_EXIST(60002, "问题不存在"),
    USER_ALREADY_LOGIN(77777, "用户在其他地方登录"),
    TOKEN_ERROR(77778, "Token过期了");
    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
