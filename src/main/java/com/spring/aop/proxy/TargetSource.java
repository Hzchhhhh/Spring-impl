package com.spring.aop.proxy;

/**
 * 用于获取AOP调用的当前“目标”
 * @author: hzc
 * @date: 2022/8/24-13:51
 */
public interface TargetSource {

    Object getTarget() throws Exception;

}
