package com.spring.interfaces;

/**
 * @author: hzc
 * @date: 2022/8/23-10:26
 * 将 beanName 传递给 bean
 * 某个bean 实现了这个接口，就能得到它的 beanName
 * 由 Spring 调用
 */
public interface BeanNameAware {

    void setBeanName(String beanName);

}
