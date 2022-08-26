package com.spring.aop;

import com.spring.aop.advisor.Advisor;

import java.util.List;

/**
 * @author: hzc
 * @date: 2022/8/23-15:59
 */
public interface AspectJAdvisorFactory {

    boolean isAspect(Class<?> clazz);

    List<Advisor> getAdvisors(Class<?> clazz);
}
