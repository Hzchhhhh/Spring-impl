package com.spring.aop.advisor;

import java.lang.reflect.Method;

/**
 *  * Part of a Pointcut: Checks whether the target method is eligible for advice.
 * @author: hzc
 * @date: 2022/8/24-11:52
 */
public interface MethodMatcher {

    /**
     * Perform static checking whether the given method matches.
     *
     * @param method      the candidate method
     * @param targetClass the target class
     * @return whether or not this method matches statically
     */
    boolean matches(Method method, Class<?> targetClass);

}
