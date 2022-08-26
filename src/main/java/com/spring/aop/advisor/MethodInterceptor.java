package com.spring.aop.advisor;

/**
 * 环绕通知
 * @author: hzc
 * @date: 2022/8/24-11:56
 */
public interface MethodInterceptor extends Interceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;
}
