package com.example.springdemo.proxy.stati;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImplProxy extends OrderServiceImpl {

    /**
     * OrderServiceImplProxy 代理对象
     * OrderServiceImpl 目标对象
     *
     * 在代理对象中调用目标对象前、后可以进行相关逻辑操作
     *
     * @return
     */
    public String pay() {
        System.out.println("支付之前。。。");
        String pay = super.pay(new Object());
        System.out.println("支付完成之后。。。。");
        return null;
    }
}
