package com.huaguoguo.util;
/**   
 * redis客户端
 */
public interface JedisClient {

	String get(String key);
	void set(String key, String value);
	void hset(String hkey, String key, String value);
	String hget(String hkey, String key);
	void del(String key);
	Long hdel(String hkey, String key);
	void expire(String key, int second);
	void incr(String key,Long number);
}
