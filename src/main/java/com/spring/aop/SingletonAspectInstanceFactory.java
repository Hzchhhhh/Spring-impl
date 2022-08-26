package com.spring.aop;

/**
 * 提供调用切面方法的类
 * 单例工厂，每次返回相同的对象，计划从容器中拿
 * @author: hzc
 * @date: 2022/8/24-13:02
 */
public class SingletonAspectInstanceFactory implements AspectInstanceFactory{

    private Object aspectInstance;

    public SingletonAspectInstanceFactory(Object aspectInstance) {
        this.aspectInstance = aspectInstance;
    }

    @Override
    public Object getAspectInstance() {
        return this.aspectInstance;
    }
}
