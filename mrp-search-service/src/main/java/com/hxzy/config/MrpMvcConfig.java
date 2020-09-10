package com.hxzy.config;

import com.hxzy.filter.FilterCors;
import com.hxzy.filter.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * mvc配置
 */
@Configuration
public class MrpMvcConfig  extends WebMvcConfigurationSupport{

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        /*放行swagger*/
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    /**
     * 登录拦截器
     */
    @Autowired
    private LoginCheckInterceptor  loginCheckInterceptor;

    @Autowired
    private FilterCors  filterCors;

    //拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(filterCors).addPathPatterns("/**");

        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/search/**");

        super.addInterceptors(registry);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }
}
