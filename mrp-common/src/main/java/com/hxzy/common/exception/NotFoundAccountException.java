package com.hxzy.common.exception;

/**
 * 账户未找到的异常
 */
public class NotFoundAccountException extends  RuntimeException {
    public NotFoundAccountException() {
        super("账户未找到");
    }

    public NotFoundAccountException(String message) {
        super(message);
    }

    public NotFoundAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
