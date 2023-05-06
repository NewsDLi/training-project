package com.example.springdemo.proxy.dynamic.jdk;


import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class PaymentServiceImplProxy implements InvocationHandler {

    private PaymentServiceImpl paymentService;

    public PaymentServiceImplProxy() {
    }

    public PaymentServiceImplProxy(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("支付之前。。。");
        return method.invoke(paymentService, args);
    }


}
