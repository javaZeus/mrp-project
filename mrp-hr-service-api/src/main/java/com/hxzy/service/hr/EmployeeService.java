package com.hxzy.service.hr;

import com.hxzy.common.service.BaseService;
import com.hxzy.common.vo.Result;
import com.hxzy.dto.hr.EmailVerifyDO;
import com.hxzy.dto.hr.LoginDO;
import com.hxzy.dto.hr.SmsDO;
import com.hxzy.dto.hr.SmsEmployeeDO;
import com.hxzy.entity.hr.Employee;
import org.apache.solr.client.solrj.SolrServerException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

public interface EmployeeService extends BaseService<Employee> {

    /**
     * 注册
     * @param employee
     * @return
     */
    Result register(Employee employee) throws IOException, MessagingException;

    Result emailVerity(@Valid EmailVerifyDO emailVerifyDO);

    /**
     * 重新邮件
     * @param emailVerifyDO
     * @return
     */
    Result resendEmail(EmailVerifyDO emailVerifyDO) throws IOException, MessagingException;

    /**
     * 员工短信认证注册
     * @param employeeDo
     * @return
     */
    Result smsRegister(@Valid SmsEmployeeDO employeeDo) throws IOException, SolrServerException;

    /**
     * 员工注册发送短信
     * @param smsDO
     * @return
     */
    Result sendSms(SmsDO smsDO);

    /**
     * 员工注册短信前端光标移到检查方法
     * @param smsDO
     * @return
     */
    Result smsValidator(SmsDO smsDO);

    /**
     * 更新员工并更新solr
     * @param employee
     * @return
     */
    Result  updateEmployeeAndSolr(Employee employee) throws IOException,SolrServerException;

    Result login(@Valid LoginDO loginDO);
}
