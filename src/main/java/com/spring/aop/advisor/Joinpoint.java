package com.spring.aop.advisor;

/**
 * 连接点，是程序执行的一个点。例如，一个方法的执行或者一个异常的处理。
 * 在 Spring AOP 中，一个连接点总是代表一个方法执行。
 * @author: hzc
 * @date: 2022/8/24-11:55
 */
public interface Joinpoint {

    Object proceed() throws Throwable;

}