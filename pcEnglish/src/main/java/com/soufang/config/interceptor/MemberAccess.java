package com.soufang.config.interceptor;

import java.lang.annotation.*;

/**
 * 方法上有这个注解就表示需要登录
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)//
@Target({ElementType.METHOD, ElementType.TYPE})//该注解修饰类中的方法
@Inherited
public @interface MemberAccess {


}
