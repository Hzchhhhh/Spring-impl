package com.spring.aop.proxy;

import com.spring.HzcApplicationContext;

/**
 * @author: hzc
 * @date: 2022/8/24-13:57
 */
public class LazyInjectTargetSource implements TargetSource {

    private final HzcApplicationContext applicationContext;
    private final String beanName;

    public LazyInjectTargetSource(HzcApplicationContext applicationContext, String beanName) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
    }

    @Override
    public Object getTarget() throws Exception {
        return applicationContext.getBean(beanName);
    }
}
