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
	private RedisTemplate<String,String> redisTemplate;

	public String get(String key) {
		String result = redisTemplate.opsForValue().get(key);
		return result;
	}

	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key,value);
	}



	public void hset(String hkey, String key, String value) {
		redisTemplate.opsForHash().put(hkey,key,value);
	}

	public String hget(String hkey, String key) {
		return ((String) redisTemplate.opsForHash().get(hkey, key));
	}



	public void del(String key) {
		redisTemplate.delete(key);
	}

	public Long hdel(String hkey, String key) {
		Long result = redisTemplate.opsForHash().delete(hkey, key);
		return result;
	}

	public void expire(String key, int second) {
		redisTemplate.expire(key,second, TimeUnit.SECONDS);
	}

	/**
	 * key对应的val增减操作
	 * @param key
	 * @param number 增减的数值
	 */
	@Override
	public void incr(String key,Long number) {
		redisTemplate.boundValueOps(key).increment(1);
	}


}
