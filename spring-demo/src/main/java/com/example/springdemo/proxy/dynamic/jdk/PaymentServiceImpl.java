package com.example.springdemo.proxy.dynamic.jdk;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean payMoney(Integer money) {
        System.out.println("支付。。。。");
        return true;
    }
}
