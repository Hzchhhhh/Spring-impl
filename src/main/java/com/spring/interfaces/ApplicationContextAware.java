package com.spring.interfaces;

import com.spring.HzcApplicationContext;

/**
 * @author: hzc
 * @date: 2022/8/23-10:28
 */
public interface ApplicationContextAware {

    void setApplicationContext(HzcApplicationContext applicationContext);

}
