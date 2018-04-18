package com.huaguoguo.util.impl;

import com.huaguoguo.util.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * redis单机版客户端
 */
@Component
public class JedisClientSingle implements JedisClient {

	@Autowired
	private RedisTemplate<String,String> stringRedisTemplate;

	public String get(String key) {
		String result = stringRedisTemplate.opsForValue().get(key);
		return result;
	}

	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key,value);
	}



	public void hset(String hkey, String key, String value) {
		stringRedisTemplate.opsForHash().put(hkey,key,value);
	}

	public String hget(String hkey, String key) {
		return ((String) stringRedisTemplate.opsForHash().get(hkey, key));
	}



	public void del(String key) {
		stringRedisTemplate.delete(key);
	}

	public Long hdel(String hkey, String key) {
		Long result = stringRedisTemplate.opsForHash().delete(hkey, key);
		return result;
	}

	public void expire(String key, int second) {
		stringRedisTemplate.expire(key,second, TimeUnit.SECONDS);
	}




}
