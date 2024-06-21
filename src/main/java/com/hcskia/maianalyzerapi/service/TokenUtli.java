package com.hcskia.maianalyzerapi.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class TokenUtli {
    //Issuer
    public static final String ISSUER = "Test.com";
    //Audience
    public static final String AUDIENCE = "Client";
    //密钥
    public static final String KEY = "huancaiskiamaise";
    //算法
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(TokenUtli.KEY);
    //Header
    public static final Map<String, Object> HEADER_MAP = new HashMap<>()
    {
        {
            put("alg", "HS256");
            put("typ", "JWT");
        }
    };

    /**
     * 生成 Token 字符串
     *
     * @param claimMap claim 数据
     * @return Token 字符串
     */
    public static String GenerateToken(Map<String, String> claimMap)
    {
        Date nowDate = new Date();
        //7天过期
        Date expireDate = TokenUtli.AddDate(nowDate, 24 * 60 * 7);
        //Token 建造器
        JWTCreator.Builder tokenBuilder = JWT.create();
        //Payload 部分，根据需求添加
        for (Map.Entry<String, String> entry : claimMap.entrySet()) {tokenBuilder.withClaim(entry.getKey(), entry.getValue());}

        //token 字符串
        String token = tokenBuilder.withHeader(TokenUtli.HEADER_MAP)//Header 部分
                .withIssuer(TokenUtli.ISSUER)//issuer
                .withAudience(TokenUtli.AUDIENCE)//audience
                .withIssuedAt(nowDate)//生效时间
                .withExpiresAt(expireDate)//过期时间
                .sign(TokenUtli.ALGORITHM);//签名，算法加密

        return token;
    }

    private static Date AddDate(Date date, Integer minute)
    {
        if (null == date)
        {
            date = new Date();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();
    }



    public static Map<String, Claim> VerifyJWTToken(String webToken) throws Exception
    {
        String[] token = webToken.split("Authorization=");
        if (token[1].equals("")) {throw new Exception("token错误");}

        //JWT验证器
        JWTVerifier verifier = JWT.require(TokenUtli.ALGORITHM).withIssuer(TokenUtli.ISSUER).build();
        //解码
        DecodedJWT jwt = verifier.verify(token[1]);//如果 token 过期，此处就会抛出异常
        //Audience
        List<String> audienceList = jwt.getAudience();
        String audience = audienceList.get(0);
        //Payload
        Map<String, Claim> claimMap = jwt.getClaims();
//        for (Map.Entry<String, Claim> entry : claimMap.entrySet())
//        {
//
//        }
        //生效时间
        Date issueTime = jwt.getIssuedAt();
        //过期时间
        Date expiresTime = jwt.getExpiresAt();

        return claimMap;
    }
}
