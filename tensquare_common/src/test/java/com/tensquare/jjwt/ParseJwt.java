package com.tensquare.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwt {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("tensquare").
                parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJ0b20iLCJpYXQiOjE1NTEwMjc5NzR9.8-tX8WAwn4x5unuwYPboeII3Hs1xpu_N7qVFMx02zus").getBody();
        System.out.println("用户id:" + claims.getId());
        System.out.println("用户名:" + claims.getSubject());
        System.out.println("登录日期:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));

    }
}
