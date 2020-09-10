package com.hxzy.filter;

import com.alibaba.fastjson.JSONObject;
import com.hxzy.common.util.JwtUtil;
import com.hxzy.common.vo.Result;
import com.hxzy.common.vo.ResultCode;
import com.hxzy.config.CurrentEmployee;
import com.hxzy.entity.hr.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 判断是否登录的拦截器
 */
@Log4j2
@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url=request.getRequestURI();
        log.debug("LoginCheckInterceptor:"+ url+",threadId:"+Thread.currentThread().getId());

        //1、判断 header中是否有
        String token=request.getHeader("X-Token");
        //没有
        if(StringUtils.isBlank(token)){
            response_info(response, ResultCode.TOKEN_ERROR);
            return false;
        }
        //2、判断令牌是否有效，转换
        try {
           Claims claims= JwtUtil.parseJwt(token);
            Object obj=claims.get("employee");
            //使用fastjson反序列化
            Employee emp=JSONObject.parseObject(obj.toString(), Employee.class);
            //放到 本地线程池中
            CurrentEmployee.set(emp);

        }catch (ExpiredJwtException ex){
            response_info(response, ResultCode.TOKEN_EXPIRED);
            return false;
        }catch (Exception ex){
            response_info(response, ResultCode.TOKEN_ERROR);
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.debug("------移出currentEmployee----");
        CurrentEmployee.remove();
    }

    /**
     * 标准的servlet输出
     * @param response
     * @param resultCode
     */
    private void response_info(HttpServletResponse response, ResultCode resultCode){
        Result result= Result.createResult(resultCode);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            PrintWriter out=response.getWriter();
             //把对象json序列化
            String json= JSONObject.toJSONString( result);
            out.println(json);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
