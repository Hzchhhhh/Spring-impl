package com.spring.interfaces;

/**
 * @author: hzc
 * @date: 2022/8/23-12:33
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
