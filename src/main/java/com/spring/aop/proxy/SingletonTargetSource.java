package com.spring.aop.proxy;

/**
 * @author: hzc
 * @date: 2022/8/24-13:55
 */
public class SingletonTargetSource implements TargetSource {

    private final Object target;

    public SingletonTargetSource(Object target) {
        this.target = target;
    }

    @Override
    public Object getTarget() throws Exception {
        return this.target;
    }
}
