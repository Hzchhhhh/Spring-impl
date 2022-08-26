package com.spring.aop;

import com.spring.HzcApplicationContext;
import com.spring.aop.advisor.Advice;
import com.spring.aop.advisor.Advisor;
import com.spring.aop.advisor.MethodMatcher;
import com.spring.aop.advisor.Pointcut;
import com.spring.aop.proxy.ProxyFactory;
import com.spring.aop.proxy.SingletonTargetSource;
import com.spring.core.OrderComparator;
import com.spring.interfaces.ApplicationContextAware;
import com.spring.interfaces.SmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  * bean 后处理器，对符合条件的 bean 进行 aop 代理增强，创建代理对象
 * @author: hzc
 * @date: 2022/8/23-15:55
 */
public class AnnotationAwareAspectJAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, ApplicationContextAware {

    private HzcApplicationContext applicationContext;

    private final AspectJAdvisorFactory advisorFactory = new DefaultAspectJAdvisorFactory();

    private List<Advisor> cachedAdvisors;

    /**
     * 记录哪些 bean 尝试过提前创建代理，无论这个 bean 是否创建了代理增强，都记录下来，
     * 等到初始化阶段进行创建代理时，检查缓存，避免重复创建代理。
     * 存储的值就是 beanName
     */
    private final Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws RuntimeException {
        // 缓存当前 bean ，表示该 bean 被提前代理了
        this.earlyProxyReferences.add(beanName);
        // 对 bean 进行提前 Spring AOP 代理
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return SmartInstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean != null) {
            // earlyProxyReferences 中不包含当前 beanName，才创建代理
            // 查看该bean是否被Spring AOP提前代理！而缓存的是原始的bean，因此如果bean被提前代理过，这此处会跳过
            // 如果bean没有被提前代理过，则进入AOP代理
            if (!this.earlyProxyReferences.contains(beanName)) {
                return wrapIfNecessary(bean, beanName);
            } else {
                // earlyProxyReferences 中包含当前 beanName，不再重复进行代理创建，直接返回
                this.earlyProxyReferences.remove(beanName);
            }
        }
        // bean 已经进行过 AOP，直接返回即可
        return bean;
    }


    // 是否开启 AOP
    private Object wrapIfNecessary(Object bean, String beanName) {
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        // 查找增强器
        List<Advisor> advisorList = findEligibleAdvisors(bean.getClass(), beanName);
        if (!advisorList.isEmpty()) {
            Object proxy = createProxy(bean.getClass(), bean, beanName, advisorList);
            return proxy;
        }
        System.out.println("………………………………………………Did not to auto-proxy user class [" + bean.getClass().getName() + "],  beanName[" + beanName + "]");
        return bean;
    }

    // 判断当前bean是否是基础类型的 Advice、Pointcut、Advisor Aspect
    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass) ||
                this.advisorFactory.isAspect(beanClass);
        if (retVal) {
            // logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
            System.out.println("………………………………………………Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
        }
        return retVal;
    }

    // 创建代理
    private Object createProxy(Class<?> targetClass, Object target, String beanName, List<Advisor> advisorList) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(new SingletonTargetSource(target));
        proxyFactory.addAdvisors(advisorList);
        proxyFactory.setInterfaces(targetClass.getInterfaces());

        System.out.println("给 " + beanName + " 创建代理，有 " + advisorList.size() + " 个切面。");
        return proxyFactory.getProxy();
    }

    // 查找增强器
    private List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        // 查找所有的增强器
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        // 找到匹配当前bean的增强器
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
        // 如果最终的 Advisor 列表不为空，再在开头位置添加一个 ExposeInvocationInterceptor
        // extendAdvisors(eligibleAdvisors);
        if (!eligibleAdvisors.isEmpty()) {
            // 给所有匹配的切面排序
            OrderComparator.sort(eligibleAdvisors);
        }
        return eligibleAdvisors;
    }

    // 找到匹配当前bean的增强器
    private List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }
        List<Advisor> eligibleAdvisors = new ArrayList<>(candidateAdvisors.size());
        Method[] methods = beanClass.getDeclaredMethods();

        // 遍历 bean 目标类型的所有方法，包括继承来的接口方法等
        // 继承的方法没写

        // 双重 for 循环
        for (Advisor advisor : candidateAdvisors) {
            MethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
            for (Method method : methods) {
                if (methodMatcher.matches(method, beanClass)) {
                    eligibleAdvisors.add(advisor);
                    break;
                }
            }
        }
        return eligibleAdvisors;
    }

    // 查找所有的增强器
    private List<Advisor> findCandidateAdvisors() {
        List<Advisor> advisors = findCandidateAdvisorsInBeanFactory();
        advisors.addAll(findCandidateAdvisorsInAspect());
        return advisors;
    }

    /**
     * 遍历 beanFactory 中所有 bean，找到被 @Aspect 注解标注的 bean，再去 @Aspect 类中封装 Advisor
     *
     * @return
     */
    private List<Advisor> findCandidateAdvisorsInAspect() {
        if (this.cachedAdvisors != null) {
            return this.cachedAdvisors;
        }
        List<Class<?>> allClass = applicationContext.getAllBeanClass();
        List<Advisor> advisors = new ArrayList<>();

        for (Class<?> cls : allClass) {
            if (this.advisorFactory.isAspect(cls)) {
                List<Advisor> classAdvisors = this.advisorFactory.getAdvisors(cls);
                advisors.addAll(classAdvisors);
            }
        }
        this.cachedAdvisors = advisors;
        return this.cachedAdvisors;
    }

    /**
     * 去容器中拿所有低级 Advisor
     *
     * @return
     */
    private List<Advisor> findCandidateAdvisorsInBeanFactory() {
        return new ArrayList<>();
    }

    @Override
    public void setApplicationContext(HzcApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}