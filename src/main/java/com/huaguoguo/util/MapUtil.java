package com.huaguoguo.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
  * @Author:huaguoguo
  * @Description: map相关的工具类
  * @Date: 2018/5/21 16:18
  */
public class MapUtil {
    /**
     * 
     * 功能描述：对象转Map
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     * 
     * @param obj Bean对象
     * @return
     * @throws Exception Map<String,Object>
     *
     */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(null!=value){
					    map.put(key, value);
					}
					
				}

			}
		} catch (Exception e) {
			 throw new RuntimeException("Bean转Map异常" +e.getMessage(),e);
		}
		return map;

	}

	/**
	 * 
	 * 功能描述：Map转对象
	 * Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	 * @param map 目标Map
	 * @param obj 目标对象
	 * @throws Exception void
	 *
	 */
	public static void transMap2Bean(Map<String, Object> map, Object obj){

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			 throw new RuntimeException("Map转Bean异常" +e.getMessage(),e);
		}

		return;

	}
}
