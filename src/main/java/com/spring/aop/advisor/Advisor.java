package com.spring.aop.advisor;

import com.spring.core.Ordered;

/**
 * @author: hzc
 * @date: 2022/8/24-11:47
 */
public interface Advisor extends Ordered {

    /**
     * 此方法应该再封装一个接口：PointcutAdvisor，放在这个接口里，这里直接放在 Advisor 接口 里了
     * @return
     */
    Pointcut getPointcut();

    Advice getAdvice();
}
