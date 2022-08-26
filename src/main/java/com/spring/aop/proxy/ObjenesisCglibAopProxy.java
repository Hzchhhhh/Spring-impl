package com.spring.aop.proxy;

/**
 * TODO 使用 cglib 动态代理
 * @author: hzc
 * @date: 2022/8/24-13:56
 */
public class ObjenesisCglibAopProxy implements AopProxy {

    private ProxyFactory proxyFactory;

    public ObjenesisCglibAopProxy(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    @Override
    public Object getProxy() {
        return null;
    }
}
