package com.huaguoguo.util;

/**
 * 
 */

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
  * @Author:huaguoguo
  * @Description: json工具类
  * @Date: 2018/6/7 15:32
  */
public abstract class JsonUtil {

	/** 空 JSON: {}. */
	public static final String EMPTY = "{}";

	/** 序列化配置. */
	protected static final SerializeConfig config = new SerializeConfig();

	/** 日志记录器. */
	protected static final Log log = LogFactory.getLog(JsonUtil.class);

	static {
		// 指定Date类型在JSON序列化的格式.
		config.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 受保护的构造方法, 防止外部构建对象实例.
	 */
	protected JsonUtil() {
		super();
	}

	/**
	 * 将Object转为json字符串;<br/>
	 * 当null==obj时, 则返回 "{}".
	 * 
	 * @param obj 对象.
	 * @return String json字符串.
	 */
	public static String toJson(Object obj) {
		return toJson(obj, EMPTY);
	}

	/**
	 * 将Object转为json字符串;<br/>
	 * 当null==obj时, 则返回 defaultValue.
	 * 
	 * @param obj 对象.
	 * @param defaultValue 默认值.
	 * @return String json字符串.
	 */
	public static String toJson(Object obj, String defaultValue) {
		if (null == obj) {
			return defaultValue;
		}

		return JSON.toJSONString(obj, config, SerializerFeature.DisableCircularReferenceDetect);
	}

	public static <T> T jsonToObj(String json,Class<T> clazz){
		T t = null;
		if (null != json && json.length() > 0) {
			t = JSONObject.parseObject(json,clazz);
		}
		return t;
	}

}
