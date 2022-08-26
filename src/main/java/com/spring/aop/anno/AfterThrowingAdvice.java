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
public class AfterThrowingAdvice extends CommonAdvice implements Advice, MethodInterceptor {

    public AfterThrowingAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    @Override
    public void setThrowingName(String name) {
        super.setThrowingName(name);
    }

    /*
     public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		}
		catch (Throwable ex) {
			if (shouldInvokeOnThrowing(ex)) {
				invokeAdviceMethod(getJoinPointMatch(), null, ex);
			}
			throw ex;
		}
	}
     */

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable ex) {
            if (shouldInvokeOnThrowing(ex)) {
                afterThrowing(ex);
            }
            throw ex;
        }
    }

    /**
     * 只有当抛出的异常是给定抛出类型的子类型时，才会调用 afterThrowing 通知。
     *
     * @param ex
     * @return
     */
    private boolean shouldInvokeOnThrowing(Throwable ex) {
        return getDiscoveredThrowingType().isAssignableFrom(ex.getClass());
    }

    public void afterThrowing(Throwable ex) throws Throwable {
        invokeAdviceMethod(null, ex);
    }

    @Override
    public int getOrder() {
        return 2;
    }

}
