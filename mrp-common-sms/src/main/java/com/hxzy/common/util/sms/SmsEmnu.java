package com.hxzy.common.util.sms;

/**
 * 短信模板枚举
 */
public enum SmsEmnu {

    /**
     * 员工注册
     */
    EMPLOYEE_REGISTER_SMS(473168,"员工注册短信认证，参数2个{0个为验证码，1个为时间（分钟）}");


    private int templateId;

    private String description;

    public int getTemplateId() {
        return templateId;
    }

    public String getDescription() {
        return description;
    }

    SmsEmnu(int templateId, String description) {
        this.templateId = templateId;
        this.description = description;
    }
}
