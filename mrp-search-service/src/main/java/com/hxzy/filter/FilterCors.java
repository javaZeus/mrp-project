package com.hxzy.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 跨域放行
 */
@Component
public class FilterCors extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---------------FilterCors---------------------");
        System.out.println(request.getHeader("origin"));
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));

        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //是否允许浏览器携带用户身份信息（cookie）

        return true;

    }


}
