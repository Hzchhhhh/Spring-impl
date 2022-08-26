package com.hzc;

import com.hzc.service.UserInterface;
import com.spring.HzcApplicationContext;

/**
 * @author: hzc
 * @date: 2022/8/22-9:59
 */
public class Test {
    public static void main(String[] args) {
        HzcApplicationContext applicationContext = new HzcApplicationContext(AppConfig.class);

        System.out.println("=================beanNames=================");
        for (String beanName : applicationContext.getBeanNames()) {
            System.out.println(beanName);
        }
        System.out.println("===========================================");

        // JDK 代理对象返回的是 com.sun.proxy.$Proxy5， 不能强转为实现，只能强转为接口
        // UserService userService = (UserService) applicationContext.getBean("userService");
        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
        System.out.println("userService: " + userService.getClass().getName());
        userService.test(100);
        System.out.println("applicationContext.getBean(\"orderService\"):  " + applicationContext.getBean("orderService"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
        userService.toString();

        // PostInterface postService = (PostInterface) applicationContext.getBean("postService");
        // postService.post();
        // System.out.println(postService.getClass().getName());
    }
}
