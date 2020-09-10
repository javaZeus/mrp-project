package com.hxzy.common.constant;

/**
 * 系统通用常量类
 */
public final class CommonConstant {

    /**
     * 安全密钥
     */
    public final static String JWT_SECURITY_PASSWORD="hxzy#_mrp$_123!_$";

    /**
     * JWT有效期是30分钟
     */
    public final static Long JWT_EXPIRED_MINUTE_TIME=1000L*60 *30;

    /**
     * 邮件注册激活标题
     */
    public final static String REGISTER_ACTIVE_EMAIL_TITLE="注册账户激户";


    /**
     * 注册员工并发送邮件redis的前缀
     */
    public final static String REDIS_PREFIX_REGISTER_EMAIL="employee:register:";
}
