package com.hzc.service;

import com.spring.anno.Autowired;
import com.spring.anno.Component;
import com.spring.anno.Scope;
import com.spring.interfaces.BeanNameAware;
import com.spring.interfaces.InitializingBean;

/**
 * @author mafei007
 * @date 2022/6/29 19:31
 */
@Component("userService")
@Scope("singleton")
// @Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean, UserInterface {

    @Autowired
    private OrderService orderService;

    @Override
    public void test(int i) {
        int k = 1 / 0;
        // String a = null;
        // System.out.println(a.length());
        System.out.println("userService#test 方法执行。。。。。参数 i 为：" + i + "。 依赖注入的 OrderService：" + orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("我是：" + this.getClass().getName() + "，我的 beanName是：" + beanName);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("我是：" + this.getClass().getName() + "，开始执行初始化方法：afterPropertiesSet()");
    }
}
