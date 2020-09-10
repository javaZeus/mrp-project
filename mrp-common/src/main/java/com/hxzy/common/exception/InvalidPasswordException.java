package com.hxzy.common.exception;

/**
 * 密码错误异常
 */
public class InvalidPasswordException extends  RuntimeException {

    public InvalidPasswordException() {
        super("密码错误");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
