package com.spring.aop.advisor.joinpoint;

/**
 * 使用在通知方法的参数上，用于获取各种信息
 * <p>
 * 注意对比 com.spring.aop.advisor.Joinpoint，这个是切入点方法执行的顶级接口
 *
 * @author: hzc
 * @date: 2022/8/24-13:14
 */
public interface JoinPoint {

    /**
     * 获取执行链中目标方法的实参
     * @return
     */
    Object[] getArgs();

    /**
     * 获取执行链中目标方法的方法名
     * @return
     */
    String getMethodName();

}
