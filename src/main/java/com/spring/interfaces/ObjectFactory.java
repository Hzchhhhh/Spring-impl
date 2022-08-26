package com.spring.interfaces;

/**
 * @author: hzc
 * @date: 2022/8/22-15:35
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject() throws RuntimeException;
}
