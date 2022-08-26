package com.spring.aop.anno;

import com.spring.aop.AspectInstanceFactory;
import com.spring.aop.advisor.Advice;
import com.spring.aop.advisor.CommonAdvice;
import com.spring.aop.advisor.MethodInterceptor;
import com.spring.aop.advisor.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author mafei007
 * @date 2022/7/7 23:23
 */
public class AfterAdvice extends CommonAdvice implements Advice, MethodInterceptor {

    public AfterAdvice(Method aspectJAdviceMethod, AspectInstanceFactory aspectInstanceFactory) {
        super(aspectJAdviceMethod, aspectInstanceFactory);
    }

    /*
    	@Override
	@Nullable
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		}
		finally {
			invokeAdviceMethod(getJoinPointMatch(), null, null);
		}
	}
     */

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            after();
        }
    }

    public void after() throws Throwable {
        invokeAdviceMethod(null,null);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
