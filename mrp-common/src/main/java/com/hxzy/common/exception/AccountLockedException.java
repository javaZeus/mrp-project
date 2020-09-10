package com.hxzy.common.exception;

/**
 * 账户被锁定
 */
public class AccountLockedException extends  RuntimeException {

    public AccountLockedException() {
        super("账户被锁定");
    }

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
