package com.example.springdemo.proxy.dynamic.cglib;

public class OrderDemoServiceImpl implements OrderDemoService {

    @Override
    public String payOrder(Integer money) {
        System.out.println("支付。。。。");
        return null;
    }
}
