package com.hxzy.controller.hr;

import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import com.hxzy.config.CurrentEmployee;
import com.hxzy.dto.hr.EmailVerifyDO;
import com.hxzy.dto.hr.LoginDO;
import com.hxzy.dto.hr.SmsDO;
import com.hxzy.dto.hr.SmsEmployeeDO;
import com.hxzy.entity.hr.Employee;
import com.hxzy.service.hr.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(value = "/hr")
@Api(value="EmployeeController",description = "员工相关操作")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @ApiOperation(value = "员工注册带有邮件发送")
    @PostMapping(value = "/email/register")
    public Result register(@Valid Employee  employee) throws IOException, MessagingException {
        return this.employeeService.register(employee);
    }

    @ApiOperation(value = "员工账户邮件激活")
    @GetMapping(value = "/email/verify")
    public Result  employeeVerify( @Valid EmailVerifyDO emailVerifyDO){
        return this.employeeService.emailVerity(emailVerifyDO);
    }

    @ApiOperation(value = "员工账户激活重发邮件")
    @GetMapping(value = "/email/resend")
    public Result employeeEmailResend(@Validated EmailVerifyDO emailVerifyDO) throws IOException, MessagingException {
        return this.employeeService.resendEmail(emailVerifyDO);
    }

    @ApiOperation(value = "员工注册短信认证注册")
    @PostMapping(value = "/sms/register")
    public Result smsRegister(@Valid SmsEmployeeDO employeeDo) throws IOException, SolrServerException {
        return this.employeeService.smsRegister(employeeDo);
    }

    @ApiOperation(value = "员工注册短信发送请求")
    @GetMapping(value = "/sms/send")
    public Result sendSms(@Validated(value = SmsDO.SendSmsState.class) SmsDO  smsDO){
        return this.employeeService.sendSms(smsDO);
    }

    @ApiOperation(value = "员工注册短信前端光标移到检查方法")
    @GetMapping(value = "/sms/check")
    public Result smsValidator(@Validated(value = SmsDO.MobileCodeValid.class) SmsDO  smsDO){
       return this.employeeService.smsValidator(smsDO);
    }


    @ApiOperation(value = "员工修改数据操作")
    @PostMapping(value = "/employee/edit")
    public Result edit(Employee employee) throws IOException, SolrServerException {
       return this.employeeService.updateEmployeeAndSolr(employee);
    }


    @ApiOperation(value = "员工登录(邮箱或手机)")
    @PostMapping(value = "/login")
    public Result login(@Valid LoginDO loginDO){

        return this.employeeService.login(loginDO);
    }


    @ApiOperation(value = "根据token取得当前用户信息")
    @GetMapping(value = "/employee/info")
    public Result getCurrentUserInfo(){
        return Result.createResult_Data(ResultCode.OK, CurrentEmployee.get());
    }

}
