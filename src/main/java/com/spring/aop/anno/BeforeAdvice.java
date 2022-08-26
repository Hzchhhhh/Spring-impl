package com.spring.aop.anno;

import com.spring.aop.AspectInstanceFactory;
import com.spring.aop.advisor.Advice;
import com.spring.aop.advisor.CommonAdvice;
import com.spring.aop.advisor.MethodInterceptor;
import com.spring.aop.advisor.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author: hzc
 * @date: 2022/8/24-13:51
 */
public class BeforeAdvice extends CommonAdvice implements Advice, MethodInterceptor {

    public BeforeAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    /*
    	public Object invoke(MethodInvocation mi) throws Throwable {
		this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
		return mi.proceed();
	}
     */

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 先调用before方法
        before();
        // 继续链式调用
        return invocation.proceed();
    }

    public void before () throws Throwable {
        invokeAdviceMethod(null,null);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
