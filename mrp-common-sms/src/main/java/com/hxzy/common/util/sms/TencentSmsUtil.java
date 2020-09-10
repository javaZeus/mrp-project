package com.hxzy.common.util.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 腾讯短信发送（使用低版本2.0,最新的3.0不能使用）
 */
@Component
@Scope(value = "prototype")
public class TencentSmsUtil {

    // 短信应用 SDK AppID
   private final int appid = 1400412221;
    // 短信应用 SDK AppKey
    private final String appkey = "d7c8335e55a4a54a4acde466f1e508fc";

    // 签名
    private final String smsSign = "易发公众号";

    //要发送的手机号
    private String phoneNumber;

    /**
     * 短信模板ID
     */
    private SmsEmnu smsEmnuTemplate=SmsEmnu.EMPLOYEE_REGISTER_SMS;

    /**
     * 发送短信的参数
     */
    private String[] params;

    /**
     * 发送短信号码
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 设定短信模板
     * @param smsEmnuTemplate
     */
    public void setSmsEmnuTemplate(SmsEmnu smsEmnuTemplate) {
        this.smsEmnuTemplate = smsEmnuTemplate;
    }

    /**
     * 设定发送短信的参数
     * @param params
     */
    public void setParams(String[] params) {
        this.params = params;
    }

    /**
     * 发送短信
     * @return
     * @throws HTTPException
     * @throws IOException
     */
    public Result sendSms() throws HTTPException, IOException {
        Result result=null;
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult smsResult = ssender.sendWithParam("86", this.phoneNumber,this.smsEmnuTemplate.getTemplateId(), this.params, smsSign, "", "");
        if(smsResult.result==0){
           result=Result.createResult(ResultCode.OK);
        }else{
           result=Result.createResult(ResultCode.SMS_SEND_FAILED);
        }
        return result;
    }












}
