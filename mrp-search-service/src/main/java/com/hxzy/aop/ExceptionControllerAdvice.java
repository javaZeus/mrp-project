package com.hxzy.aop;


import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * SpringMVC的全局统一 异步 处理 增强
 */
@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {

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


    /**
     * 查询solr失败的
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {SolrServerException.class})
    public Result solrServerException(SolrServerException ex){
        log.error(ex);
        return Result.createResult(ResultCode.SOLR_SEARCH_ERRRO);
    }
}
