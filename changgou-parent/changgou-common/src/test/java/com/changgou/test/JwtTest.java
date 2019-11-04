package com.changgou.test;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    /**
     * 创建jwt令牌
     */
    @Test
    public void testCreateJwt(){
        JwtBuilder builder = Jwts.builder()
                .setId("111")                   //唯一编号
                .setSubject("小黑")             //设置主题  可以是JSON数据
                .setIssuedAt(new Date())       ///设置签发日期
//                .setExpiration(new Date())     //设置过期时间
                .signWith(SignatureAlgorithm.HS256,"itcast"); //设置签名 使用HS256算法，并设置SecretKey(字符串)
        //自定义数据
        Map<String,Object> userInfo=new HashMap<>();
        userInfo.put("username","王五");
        userInfo.put("age",21);
        userInfo.put("address","深圳");
        builder.addClaims(userInfo);
        //构建  并返回一个字符串
        System.out.println(builder.compact());
    }


    /**
     * 解析jwt数据信息
     */
    @Test
    public void testParseJwt(){
        String
compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTEiLCJzdWIiOiLlsI_pu5EiLCJpYXQiOjE1NjcyNTg1NDAsImFkZHJlc3MiOiLmt7HlnLMiLCJhZ2UiOjIxLCJ1c2VybmFtZSI6IueOi-S6lCJ9.hGVrOPVuqsctMKT1YEbV-fSzXK3r3PDUJ3cnUF4xpjE";
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")   //要设置key的为itcase
                .parseClaimsJws(compactJwt) //解析   compactJwt
                .getBody();                 //得到结构体
        System.out.println(claims);
    }
}
