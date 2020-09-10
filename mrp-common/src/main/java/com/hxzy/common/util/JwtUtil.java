package com.hxzy.common.util;

import com.alibaba.fastjson.JSONObject;
import com.hxzy.common.constant.CommonConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JwtUTIL
 */
public class JwtUtil {

    private static final String  JWTKEYS="hxzy#_mrp$_123!_$";


    /**
     * 创建JWT令牌
     * @return
     */
    public static String createJwt(String jwtKey,Object obj){
        Claims  claims=Jwts.claims();
        claims.put(jwtKey, JSONObject.toJSONString(obj));

        Date expiresDate = new Date(System.currentTimeMillis() + CommonConstant.JWT_EXPIRED_MINUTE_TIME);// expire_time为token有效时长, 单位毫秒

        String jwtStr=Jwts.builder()
                .setSubject(obj.toString())
                .setClaims(claims)
                .setExpiration(expiresDate)  //设定过期时间
                .signWith(SignatureAlgorithm.HS256, CommonConstant.JWT_SECURITY_PASSWORD)
                .compact();
        return jwtStr;

    }



    /**
     * 解析jwt串  https://github.com/jwtk/jjwt
     * @param jwtStr
     * @return
     */
    public static Claims parseJwt(String jwtStr){
        Claims claims=Jwts.parser().setSigningKey(JWTKEYS).parseClaimsJws(jwtStr).getBody();
        return claims;
    }

    public static void main(String[] args) {
        String jwtStr="";

        try{
            Claims claims=parseJwt(jwtStr);
            Object obj=claims.get("employee");
            //使用fastjson反序列化
            //Employee emp=JSONObject.parseObject(obj.toString(), Employee.class);
           // System.out.println(emp);

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt=new Date(1596355576L*1000);
            System.out.println(sdf.format(dt));


        }catch(ExpiredJwtException e1){
            e1.printStackTrace();
        }

    }


}
