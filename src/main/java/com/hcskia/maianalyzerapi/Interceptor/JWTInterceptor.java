package com.hcskia.maianalyzerapi.Interceptor;

import com.hcskia.maianalyzerapi.service.TokenUtli;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JWTInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //从请求头内获取token
        String token = request.getHeader("authorization");

        //验证令牌，如果令牌不正确会出现异常会被全局异常处理
        return TokenUtli.VerifyJWTToken(token);
    }
}