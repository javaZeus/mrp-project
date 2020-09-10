package com.hxzy.service.hr.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.qcloudsms.httpclient.HTTPException;
import com.hxzy.common.constant.CommonConstant;
import com.hxzy.common.exception.AccountLockedException;
import com.hxzy.common.exception.AccountNotActivedException;
import com.hxzy.common.exception.InvalidPasswordException;
import com.hxzy.common.exception.NotFoundAccountException;
import com.hxzy.common.service.impl.BaseServiceImpl;
import com.hxzy.common.util.AssertUtil;
import com.hxzy.common.util.BCryptUtil;
import com.hxzy.common.util.mail.EmailUtil;
import com.hxzy.common.util.sms.SmsEmnu;
import com.hxzy.common.util.sms.TencentSmsUtil;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import com.hxzy.dto.hr.EmailVerifyDO;
import com.hxzy.dto.hr.LoginDO;
import com.hxzy.dto.hr.SmsDO;
import com.hxzy.dto.hr.SmsEmployeeDO;
import com.hxzy.entity.hr.Department;
import com.hxzy.entity.hr.Dictinfo;
import com.hxzy.entity.hr.Employee;
import com.hxzy.mapper.hr.EmployeeMapper;
import com.hxzy.service.hr.DepartmentService;
import com.hxzy.service.hr.DictinfoService;
import com.hxzy.service.hr.EmployeeService;
import com.hxzy.util.RegisterEamilSendContent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Service
@Log4j2
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService{

    private EmployeeMapper employeeMapper;

    @Autowired
    private SolrClient solrClient;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TencentSmsUtil tencentSmsUtil;

    @Autowired
    private EmailUtil  emailUtil;


    @Autowired
    private DictinfoService dictinfoService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RegisterEamilSendContent registerMailContent;

    @Autowired
    public void setEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
        super.setBaseMapper(employeeMapper);
    }


    //spring事务默认回滚异常是 RuntimeException
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Result register(Employee employee) throws IOException, MessagingException {
        AssertUtil.assertIsNull(employeeMapper);

        Result result=null;
        //写入数据库
        employee.setStatus(0);
        //密码更改
        String pwd=employee.getPassword();
        String pwd5= BCryptUtil.hashpw(pwd, BCryptUtil.gensalt());
        employee.setPassword(pwd5);

        int count=this.employeeMapper.insert(employee);
        if(count>0){
          result = sendMail(employee.getEmail());

        }else{
            result=Result.createResult(ResultCode.SAVE_DATABASE_ERROR);
        }

        return result;
    }

    private Result sendMail(String  email) throws IOException, MessagingException {
        Result result =null ;
        //生成uuid-->redis  (写入redis)     rabbitmq
        String uuid= UUID.randomUUID().toString();
        String redisKey=CommonConstant.REDIS_PREFIX_REGISTER_EMAIL+uuid;
        //uuid    邮件名称
        this.stringRedisTemplate.opsForValue().set(redisKey,email,2, TimeUnit.DAYS);

        //发邮件 -->    rabbitmq
        registerMailContent.setUuid(uuid);
        registerMailContent.setEmail(this.emailUtil.getFrom());
        //设定读取邮件的模板
        this.emailUtil.setEmailContentTemplate(this.registerMailContent);

        //发送邮件
        this.emailUtil.sendHtmlMail(email, CommonConstant.REGISTER_ACTIVE_EMAIL_TITLE);

        result=Result.createResult(ResultCode.OK);
        //把邮件模板类设为null
        this.emailUtil.setEmailContentTemplate(null);

        return result;
    }

    //根据邮件地址激活账户
    @Override
    public Result emailVerity(@Valid EmailVerifyDO emailVerifyDO) {
        Result result=null;
        //1、根据rediskey 判断是否存在
        String redisKey=CommonConstant.REDIS_PREFIX_REGISTER_EMAIL+emailVerifyDO.getSn().trim();

        String redisValue=this.stringRedisTemplate.opsForValue().get(redisKey);
        if(StringUtils.isBlank(redisValue)){
              result=Result.createResult(ResultCode.EMIAL_REDIS_KEY_EXPIRED);
        }else{
            //2、根据rediskey的值 判断要激活的邮件是否正确
            if(redisValue.equals(emailVerifyDO.getMn().trim())){
                //3、更新employee 状态=1
                // 条件包装类
                UpdateWrapper<Employee>  updateWrapper=new UpdateWrapper<Employee>();
                updateWrapper.eq("email",emailVerifyDO.getMn() );

                //不想使用对象，直接对某列赋值
                updateWrapper.set("status",1);

                this.employeeMapper.update(null,  updateWrapper);

                // 移出redis里面的key
                this.stringRedisTemplate.delete(redisKey);

                //你根据email 查询出用户信息
                // 写入到solr中做查询用
                //this.addSolr(employee);

                result=Result.createResult(ResultCode.EMIAL_ACTIVE_SUCCESS);
            }else{
                result=Result.createResult(ResultCode.EMIAL_REDIS_VALUE_Valid);
            }
        }

        return result;
    }

    /**
     * 重发邮件
     * @param emailVerifyDO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Result resendEmail(EmailVerifyDO emailVerifyDO)  throws IOException, MessagingException  {
         //1、根据员工账户和状态查询==0不允许操作
        Result result=null;

        QueryWrapper<Employee>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("email", emailVerifyDO.getMn().trim());
        queryWrapper.eq("status",0);
        Employee  emp=this.employeeMapper.selectOne(queryWrapper);
        if(emp==null){
            result=Result.createResult(ResultCode.EMIAL_REDIS_VALUE_Valid);
        }else{
            //走正常邮件发送流程
            //删除rediskey
            String redisKey=CommonConstant.REDIS_PREFIX_REGISTER_EMAIL+emailVerifyDO.getSn().trim();
            this.stringRedisTemplate.delete(redisKey);
             result=sendMail(emailVerifyDO.getMn());
        }

        return result;
    }


    /**
     * 短信证码注册
     * @param employeeDo
     * @return
     */
    @Override
    public Result smsRegister(@Valid SmsEmployeeDO employeeDo) throws IOException, SolrServerException {
        AssertUtil.assertIsNull(employeeMapper);
        Result result=null;

        //再做一次redis中的key 是否与传过来key一致
        String redisKey=CommonConstant.REDIS_PREFIX_REGISTER_EMAIL+ employeeDo.getMobile();
        String redisValue=stringRedisTemplate.opsForValue().get(redisKey);
        //找不到
        if(redisValue==null ||  !redisValue.equals(employeeDo.getCode().trim()) ){
             result=Result.createResult(ResultCode.SMS_CODE_ERRRO);
        }else{
            //写入数据库的值
            Employee  employee=new Employee();
            //copy入参数到实体类中
            BeanUtils.copyProperties(employeeDo, employee);
            //写入数据库
            employee.setStatus(1);
            //密码更改
            String pwd=employee.getPassword();
            String pwd5= BCryptUtil.hashpw(pwd, BCryptUtil.gensalt());
            employee.setPassword(pwd5);

            int count=this.employeeMapper.insert(employee);

            if(count>0){
                //删除redis
                this.stringRedisTemplate.delete(redisKey);

                //写入到solr中做查询用
                this.addSolr(employee);


                result=Result.createResult(ResultCode.OK);
            }else{
                result=Result.createResult(ResultCode.SAVE_DATABASE_ERROR);
            }
        }

        return result;
    }

    /**
     * 写入到solr服务器中
     * @param emp
     */
    private void addSolr(Employee emp) throws IOException, SolrServerException {
        SolrInputDocument  document=new SolrInputDocument();

        //查询
        List<Integer> ids=new ArrayList<>();
        ids.add(emp.getSex());
        ids.add(emp.getPositionId());
        Result result=this.dictinfoService.selectBatchIds(ids);
        List arr=(List)result.getData();

        //查询部门
        Result dept_Result=this.departmentService.selectById(emp.getDepartmentId());


        document.addField("id", emp.getId());
        document.addField("emp_name",emp.getName());
        document.addField("emp_mobile",emp.getMobile());
        document.addField("emp_sex",emp.getSex());

        Dictinfo dict_sex=(Dictinfo) arr.get(0);
        document.addField("emp_sexName", dict_sex.getName());

        document.addField("emp_email",emp.getEmail());
        document.addField("emp_birthday",emp.getBirthday());
        document.addField("emp_departmentId",emp.getDepartmentId());

        Department department= (Department) dept_Result.getData();
        document.addField("emp_departmentName",department.getName());
        document.addField("emp_positionId",emp.getPositionId());

        Dictinfo dict_posi=(Dictinfo) arr.get(1);
        document.addField("emp_positionName",dict_posi.getName());

        document.addField("emp_avatar",emp.getAvatar());
        document.addField("emp_wechatid",emp.getWechatid());
        document.addField("emp_status",emp.getStatus());
        this.solrClient.add(document);
        this.solrClient.commit();
    }


    /**
     * 员工注册发送短信
     * @param smsDO
     * @return
     */
    @Override
    public Result sendSms(SmsDO smsDO) {
        Result result=null;
        //短信模板
        this.tencentSmsUtil.setSmsEmnuTemplate(SmsEmnu.EMPLOYEE_REGISTER_SMS);
        //短信参数
        Random rd=new Random();
        String[] params=new String[2];
        //4位数 1000  -  9999
        //     0        8999
        params[0]= (rd.nextInt(9000)+1000)+"";
        params[1]="3";
        this.tencentSmsUtil.setParams(params);
        //发送给谁
        this.tencentSmsUtil.setPhoneNumber(smsDO.getMobile().trim());
         //发送
        try {
            //存入到redis中
            String redisKey=CommonConstant.REDIS_PREFIX_REGISTER_EMAIL+ smsDO.getMobile();
            this.stringRedisTemplate.opsForValue().set(redisKey, params[0],Long.parseLong(params[1]), TimeUnit.MINUTES);

            result=this.tencentSmsUtil.sendSms();

            result.setData(params[0]);

        } catch (HTTPException e) {
             log.error(e);
             result=Result.createResult(ResultCode.SMS_SEND_FAILED);
        } catch (IOException e) {
             log.error(e);
            result=Result.createResult(ResultCode.SMS_SEND_FAILED);
        }catch(Exception e){
            log.error(e);
            result=Result.createResult(ResultCode.SMS_SEND_FAILED);
        }
        return result;
    }


    @Override
    public Result updateEmployeeAndSolr(Employee employee) throws IOException, SolrServerException {
        Result result=super.updateById(employee);
        if(result.getCode()==0){
            //更新solr
            this.addSolr(employee);
        }
        return result;
    }


    /**
     * 员工登录
     * @param loginDO
     * @return
     */
    @Override
    public Result login(@Valid LoginDO loginDO) {
        //1、判断到底是邮件还是手机
        QueryWrapper<Employee>  queryWrapper=new QueryWrapper<Employee>();

        String emailReg="^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        Pattern pattern=Pattern.compile(emailReg);
        Matcher matcher=pattern.matcher(loginDO.getUsername());
        //是邮箱
        if(matcher.matches()){
            queryWrapper.eq("email",loginDO.getUsername());
        }else{
            //不是邮箱
            queryWrapper.eq("mobile",loginDO.getUsername());
        }

        Employee  employeeDB=this.employeeMapper.selectOne(queryWrapper);
        if(employeeDB==null){
            throw new NotFoundAccountException();
        }

        //2、判断密码对不对
        if(!BCryptUtil.checkpw(loginDO.getPassword(),   employeeDB.getPassword())){
            throw new InvalidPasswordException();
        }

        //3、判断状态 未激活
        if(employeeDB.getStatus()==0){
            throw new AccountNotActivedException();
        }

        if(employeeDB.getStatus()==2){
            throw new AccountLockedException();
        }

        //3、用户名和密码都是对的,  以前使用session来存储，现在前后端分离，使用 jwt来处理
        Claims claims= Jwts.claims();
        claims.put("employee", JSONObject.toJSONString(employeeDB));

        long expire_time= 1000L*60 *30;   //30分钟
        Date expiresDate = new Date(System.currentTimeMillis() + expire_time);// expire_time为token有效时长, 单位毫秒

        String jwtStr=Jwts.builder()
                .setSubject(employeeDB.getMobile())
                .setClaims(claims)
                .setExpiration(expiresDate)  //设定过期时间
                .signWith(SignatureAlgorithm.HS256,CommonConstant.JWT_SECURITY_PASSWORD)
                .compact();

        return Result.createResult_Data(ResultCode.OK,jwtStr);

    }

    /**
     * 员工注册短信前端光标移到检查方法
     * @param smsDO
     * @return
     */
    @Override
    public Result smsValidator(SmsDO smsDO) {
        return null;
    }



}
