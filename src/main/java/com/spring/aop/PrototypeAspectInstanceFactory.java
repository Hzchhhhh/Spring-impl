package com.spring.aop;

/**
 * 提供调用切面方法的类
 * 每次返回新的切面对象
 * @author: hzc
 * @date: 2022/8/24-13:04
 */
public class PrototypeAspectInstanceFactory implements AspectInstanceFactory{

    private Class<?> clazz;

    public PrototypeAspectInstanceFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getAspectInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
