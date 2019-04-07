package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component

public class JwtIntercepter implements HandlerInterceptor {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("经过了拦截器");
        //无论header中的token情况如何，该拦截器都要正常放行请求
        //具体的情况都在具体的业务代码中进行处理
        String header = request.getHeader("Authorization");
        if (header!=null&&!"".equals(header)) {
            if(header.startsWith("Bearer")) {
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    /*if (roles != null && roles.equals("admin")) {
                        request.setAttribute("claims_admin",token);
                    }*/
                    if (roles != null && roles.equals("user")) {
                        request.setAttribute("claims_user",token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌错误");
                }
            }
        }
        return true;
    }
}
