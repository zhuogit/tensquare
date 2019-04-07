package com.tensquare.jjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("tom")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"tensquare");
        System.out.println(jwtBuilder.compact());
    }
}
