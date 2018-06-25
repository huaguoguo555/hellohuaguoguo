package com.huaguoguo.annotation;

import java.lang.annotation.*;

/**
  * @Author:huaguoguo
  * @Description: 校验授权token
  * @Date: 2018/6/25 14:09
  */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthToken {

}
