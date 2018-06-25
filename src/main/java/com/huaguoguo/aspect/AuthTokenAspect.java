package com.huaguoguo.aspect;

import com.huaguoguo.annotation.CheckAuthToken;
import com.huaguoguo.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthTokenAspect {

    Logger logger = null;

    @Autowired
    private TokenService tokenService;

    @Before("@annotation(checkAuthToken)")
    public void beforeMethod(JoinPoint point, CheckAuthToken checkAuthToken){
        MethodSignature signature = (MethodSignature) point.getSignature();
        //载入日志对象
        logger = LoggerFactory.getLogger(signature.getDeclaringType());
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 获取请求地址
        String requestPath = request.getRequestURI();
        //获取请求token
        String token = request.getHeader("authToken");
        //校验authToken
        tokenService.checkToken(token);
    }
}
