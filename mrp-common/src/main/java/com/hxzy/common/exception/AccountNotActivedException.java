package com.hxzy.common.exception;

/**
 * 账户还未激活
 */
public class AccountNotActivedException extends  RuntimeException {

    public AccountNotActivedException() {
        super("账户还未激活，请激活再使用");
    }

    public AccountNotActivedException(String message) {
        super(message);
    }

    public AccountNotActivedException(String message, Throwable cause) {
        super(message, cause);
    }
}
