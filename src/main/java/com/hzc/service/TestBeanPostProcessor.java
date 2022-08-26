package com.hzc.service;

import com.spring.interfaces.BeanPostProcessor;

/**
 * @author mafei007
 * @date 2022/6/29 23:27
 */
// @Component
public class TestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        // aop
        /*if (beanName.equals("userService")) {
            Object proxyInstance = Proxy.newProxyInstance(TestBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println(bean.getClass().getName() + "." + method.getName() + ", 切面逻辑...");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }*/
        System.out.println("🐷🐷 BeanPostProcessor: " + beanName);
        return bean;
    }
}
