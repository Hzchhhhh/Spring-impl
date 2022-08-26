package com.spring.aop.anno;

import com.spring.aop.AspectInstanceFactory;
import com.spring.aop.advisor.Advice;
import com.spring.aop.advisor.CommonAdvice;
import com.spring.aop.advisor.MethodInterceptor;
import com.spring.aop.advisor.MethodInvocation;
import com.spring.aop.advisor.joinpoint.MethodInvocationProceedingJoinPoint;
import com.spring.aop.advisor.joinpoint.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author: hzc
 * @date: 2022/8/24-13:51
 */
public class AroundAdvice extends CommonAdvice implements Advice, MethodInterceptor {

    public AroundAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    /*
    	@Override
	@Nullable
	public Object invoke(MethodInvocation mi) throws Throwable {
		if (!(mi instanceof ProxyMethodInvocation)) {
			throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
		}
		ProxyMethodInvocation pmi = (ProxyMethodInvocation) mi;
		ProceedingJoinPoint pjp = lazyGetProceedingJoinPoint(pmi);
		JoinPointMatch jpm = getJoinPointMatch(pmi);
		return invokeAdviceMethod(pjp, jpm, null, null);
	}

    protected ProceedingJoinPoint lazyGetProceedingJoinPoint(ProxyMethodInvocation rmi) {
        return new MethodInvocationProceedingJoinPoint(rmi);
    }
     */

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ProceedingJoinPoint pjp = getProceedingJoinPoint(invocation);
        return around(pjp);
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return invokeAdviceMethod(pjp, null);
    }

    protected ProceedingJoinPoint getProceedingJoinPoint(MethodInvocation mi) {
        return new MethodInvocationProceedingJoinPoint(mi);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
