package com.hxzy.util;

import com.hxzy.common.util.mail.EmailContentTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * 注册邮件邮件发送消息
 */
@Component
public class RegisterEamilSendContent extends EmailContentTemplate {

    private String uuid;
    private String email;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmailContent() throws IOException {
        String emailContent=super.checkAndGetEmailContent();
        return emailContent.toString().replace("${key}",uuid).replace("${mail}",email);
}

    @Override
    protected InputStream fileInputStream() {
        InputStream  in= EmailContentTemplate.class.getResourceAsStream("/email/register.html");
        return in;
    }
}
