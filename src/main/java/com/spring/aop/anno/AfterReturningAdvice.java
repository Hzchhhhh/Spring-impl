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
public class AfterReturningAdvice extends CommonAdvice implements Advice, MethodInterceptor {

    public AfterReturningAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    /*
    	@Override
	@Nullable
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object retVal = mi.proceed();
		this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
		return retVal;
	}

     */

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 先进行链式调用
        Object retVal = invocation.proceed();
        // 再调用afterReturning方法
        afterReturning();
        return retVal;
    }

    public void afterReturning() throws Throwable {
        invokeAdviceMethod(null,null);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
