package com.spring.aop.advisor;

import com.spring.core.Ordered;

/**
 *
 * 通知
 * Spring 中此接口并没有实现 Ordered，而是使用别的方法进行排序
 * @author: hzc
 * @date: 2022/8/24-11:46
 */
public interface Advice extends Ordered {
}
