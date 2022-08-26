package com.spring.aop.proxy;

import com.spring.aop.advisor.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 源码中 ProxyFactory 继承了多个类，这个我们为了方便将其父类中的成员变量直接加了进来
 * @author: hzc
 * @date: 2022/8/24-13:52
 */
public class ProxyFactory {

    private List<Advisor> advisorList;
    private TargetSource targetSource;
    private List<Class<?>> interfaces;
    private boolean proxyTargetClass;
    private static final Class<?>[] EMPTY_CLASS_ARRAY = {};

    /**
     * 是否允许代理对象作为 ThreadLocal 通过 AopContext 访问
     */
    private boolean exposeProxy = true;

    public ProxyFactory() {
        this.proxyTargetClass = false;
        this.advisorList = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    // 为简化源码没有写 addAdvice() 方法，源码中对 Advice 对象封装后再调用 addAdvisors()
    public void addAdvisors(List<Advisor> advisorList) {
        this.advisorList.addAll(advisorList);
    }

    // 重头戏
    public Object getProxy() {
        AopProxy aopProxy = createAopProxy();
        return aopProxy.getProxy();
    }

    public AopProxy createAopProxy() {
        // 这里简化了条件判断
        if (isProxyTargetClass()) {
            return new ObjenesisCglibAopProxy(this);
        } else {
            // 有接口
            if (!this.interfaces.isEmpty()) {
                return new JdkDynamicAopProxy(this);
            } else {
                // 没接口
                return new ObjenesisCglibAopProxy(this);
            }
        }
    }

    /**
     * TODO 没有实现动态通知调用
     * 得到此 method 的拦截器链，就是一堆环绕通知
     * 需要根据 invoke 的 method 来做进一步确定，过滤出应用在这个 method 上的 Advice
     *
     * @param method
     * @param targetClass
     * @return
     */
    // 该方法将获取到所有与当前 method 匹配的 advice (增强)
    public List<Interceptor> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<Interceptor> interceptorList = new ArrayList<>(this.advisorList.size());
        for (Advisor advisor : this.advisorList) {
            MethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
            // 切点表达式匹配才添加此 MethodInterceptor
            if (methodMatcher.matches(method, targetClass)) {
                Advice advice = advisor.getAdvice();
                if (advice instanceof MethodInterceptor) {
                    interceptorList.add((MethodInterceptor) advice);
                }
            }
        }
        return interceptorList;
    }

    // 将 target 对象封装成 TargetSource 对象，为什么要多此一举呢？
    // 其实 TargetSource 的目的是为了做对象池和多例用的，也就是说每次代理都从池中获取对象
    public void setTarget(Object target) {
        setTargetSource(new SingletonTargetSource(target));
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void addInterface(Class<?> intf) {
        // 校验如果不是接口则抛出异常
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        // 避免重复添加相同的接口
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }

    // 设置接口数组
    public void setInterfaces(Class<?>... interfaces) {
        this.interfaces.clear();
        for (Class<?> intf : interfaces) {
            addInterface(intf);
        }
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(EMPTY_CLASS_ARRAY);
    }

    public boolean exposeProxy() {
        return exposeProxy;
    }

    public void setExposeProxy(boolean exposeProxy) {
        this.exposeProxy = exposeProxy;
    }
}
