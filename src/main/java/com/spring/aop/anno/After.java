package com.spring.aop.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hzc
 * @date: 2022/8/24-13:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // @Retention作用是定义被它所注解的注解保留多久
public @interface After {
    String value();
}
