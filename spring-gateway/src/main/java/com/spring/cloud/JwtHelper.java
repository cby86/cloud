package com.spring.cloud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;
import java.util.Map;

public class JwtHelper {
    private static final String  SECRET = "internet_plus";

    //发布者 后面一块去校验
    private static final String  ISSUER = "HMACSHA256";

    //生成token的操作
    public static String genToken(Map<String, String> claims){
        try {
            //签名算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addDays(new Date(), 1));
            //相当于将claims存储在token中
            claims.forEach((k,v) -> builder.withClaim(k, v));
            return builder.sign(algorithm).toString();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    //验证token
    public static Map<String, Claim> verifyToken(String token)  {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt =  verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        return map;
    }
}
