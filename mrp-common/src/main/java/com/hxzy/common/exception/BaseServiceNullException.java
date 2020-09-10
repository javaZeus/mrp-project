package com.hxzy.common.exception;

/**
 * 业务逻辑层BaseService空指针异常
 */
public class BaseServiceNullException extends RuntimeException {

    public BaseServiceNullException() {
        super("业务逻辑层BaseService未赋值，空指针异常");
    }

    public BaseServiceNullException(String message) {
        super(message);
    }

    public BaseServiceNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
