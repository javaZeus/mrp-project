package com.hxzy.common.util.mail;

import com.hxzy.common.exception.EmailContentNullException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * 邮件模板
 */
@Component
@Log4j2
public  class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发件者
     */
    @Value("${spring.mail.username}")
    private String from;

    public String getFrom() {
        return from;
    }

    //模板方法
    private EmailContentTemplate emailContentTemplate=null;

    /**
     * 设定邮件内容对象
     * @param emailContentTemplate
     */
    public void setEmailContentTemplate(EmailContentTemplate emailContentTemplate) {
        this.emailContentTemplate = emailContentTemplate;
    }


    /**
     *  发HTML邮件信息
     * @param to  收件人是谁
     * @param subject  标题是什么
     */
     public void sendHtmlMail(String to, String subject) throws IOException, MessagingException {
        if(this.emailContentTemplate==null){
            throw new EmailContentNullException();
        }

         MimeMessage message = mailSender.createMimeMessage();

        //可以上传附件的
         MimeMessageHelper helper = new MimeMessageHelper(message, true);
         helper.setFrom(from);
         helper.setTo(to);
         helper.setSubject(subject);
         helper.setText(this.emailContentTemplate.getEmailContent(),true);
         mailSender.send(message);

    }



}
