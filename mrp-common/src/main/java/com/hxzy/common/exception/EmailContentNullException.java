package com.hxzy.common.exception;

/**
 * 邮件内容不能为空的异常
 */
public class EmailContentNullException extends RuntimeException {

    public EmailContentNullException() {
        super("邮件内容不允许为空");
    }

    public EmailContentNullException(String message) {
        super(message);
    }

    public EmailContentNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
