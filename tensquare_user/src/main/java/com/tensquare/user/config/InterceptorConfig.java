package com.tensquare.user.config;

import com.tensquare.user.interceptor.JwtIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtIntercepter jwtIntercepter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        //声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
}
