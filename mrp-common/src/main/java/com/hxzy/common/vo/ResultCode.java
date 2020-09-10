package com.hxzy.common.vo;

/**
 * 异常码
 */
public enum ResultCode {
    /**
     * 操作成功
     */
    OK(0,"操作成功"),
    /**
     * 操作失败
     */
    ERROR(500,"操作失败"),

    /**
     * 参数有误
     */
    VALIDATOR_ERROR(400,"参数有误"),

    /**
     * 参数有误
     */
    LOGIN_FALIED(501,"用户名或密码错误"),
    /**
     * token无效
     */
    TOKEN_ERROR(50008,"token无效"),

    /**
     * token过期
     */
    TOKEN_EXPIRED(50014,"token过期"),
    /**
     * token过期
     */
    NullPointer(503,"对象不允许为空"),

    SAVE_DATABASE_OK(0,"数据库操作成功"),

    SAVE_DATABASE_ERROR(505,"数据库操作失败"),

    NOT_FOUND_DATA_BY_ID(506,"数据库没有找到该主键相关的数据"),

    EMIAL_REDIS_KEY_EXPIRED(507,"链接失败，请到邮件里面点击重新发送"),
    EMIAL_REDIS_VALUE_Valid(508,"此链接地址有异常，请重试"),
    EMIAL_ACTIVE_SUCCESS(0,"通过邮件方式，账户激活成功"),
    EMIAL_SEND_FAILED(509,"邮件发送失败，操作取消"),
    SMS_SEND_FAILED(510,"短信发送失败"),
    SMS_CODE_ERRRO(511,"短信验证码有误，请重试!"),
    SOLR_SEARCH_ERRRO(600,"搜索服务失败!");


    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
