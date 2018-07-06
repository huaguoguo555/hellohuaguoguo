package com.huaguoguo.service.impl;

import com.huaguoguo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    /**
     * token过期时间，小时
     */
    private static final Long TOKEN_EXPIRES_INIT_HOUR = 96L;

    /**
     * 创建一个用户token存入redis
     * @param userId 指定用户的id
     * @return
     */
    @Override
    public String createToken(String userId) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.boundValueOps(token).set(userId,TOKEN_EXPIRES_INIT_HOUR, TimeUnit.HOURS);
        redisTemplate.boundValueOps(userId).set("0",TOKEN_EXPIRES_INIT_HOUR,TimeUnit.HOURS);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)){
            return false;
        }
        String userId = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(userId)){
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(token).expire(TOKEN_EXPIRES_INIT_HOUR,TimeUnit.HOURS);
        redisTemplate.boundValueOps(userId).expire(TOKEN_EXPIRES_INIT_HOUR,TimeUnit.HOURS);
        return true;
    }

    @Override
    public String getUserInfoId(String token) {
        String userId = redisTemplate.opsForValue().get(token);
        return userId;
    }

    @Override
    public void deleteToken(String userId) {

    }

    @Override
    public Long getUserId() {
        return null;
    }
}
