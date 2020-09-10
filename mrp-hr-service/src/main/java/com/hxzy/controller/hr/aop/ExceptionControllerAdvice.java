package com.hxzy.controller.hr.aop;


import com.hxzy.common.exception.AccountLockedException;
import com.hxzy.common.exception.AccountNotActivedException;
import com.hxzy.common.exception.InvalidPasswordException;
import com.hxzy.common.exception.NotFoundAccountException;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * SpringMVC的全局统一 异步 处理 增强
 */
@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {

    //处理hibernate值的认证统一
    @ExceptionHandler(BindException.class)
    public Result hibernateValidatorBindingException(BindException e){
       ObjectError error=e.getAllErrors().get(0);
       return Result.createResult(ResultCode.VALIDATOR_ERROR, error.getDefaultMessage());
    }

    /**
     * 令牌过期
     * @return
     */
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public Result jwtParseException(){
      return Result.createResult(ResultCode.TOKEN_EXPIRED);
    }

    //令牌被更改了或者 错误 了
    @ExceptionHandler(value = {MalformedJwtException.class})
    public Result jwtParseMalformedJwtException(){
        return Result.createResult(ResultCode.TOKEN_ERROR);
    }


    //处理登录异常(用户名不对，密码不对)
    @ExceptionHandler(value = {NotFoundAccountException.class, InvalidPasswordException.class})
    public Result LoginFailedException(){
        return Result.createResult(ResultCode.LOGIN_FALIED);
    }

    //处理登录异常(账户被锁定，或未被激活)
    @ExceptionHandler(value = {AccountNotActivedException.class, AccountLockedException.class})
    public Result AccountException(RuntimeException e){
        Result result= Result.createResult(ResultCode.LOGIN_FALIED);
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * 邮件发送失败,操作取消
     * @return
     */
    @ExceptionHandler(value = {AuthenticationFailedException.class, MessagingException.class, MailSendException.class})
    public Result emailSendException(Exception ex){
        log.error(ex);
        return Result.createResult(ResultCode.EMIAL_SEND_FAILED);
    }


    /**
     * 写入solr失败
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {SolrServerException.class})
    public Result solrServerException(SolrServerException ex){
        log.error(ex);
        return Result.createResult(ResultCode.SAVE_DATABASE_ERROR);
    }
}
