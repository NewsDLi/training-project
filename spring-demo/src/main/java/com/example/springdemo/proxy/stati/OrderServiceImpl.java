package com.example.springdemo.proxy.stati;

import org.springframework.stereotype.Service;

/**
 * 实现OrderService中pay方法
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public String pay(Object param) {
        System.out.println("支付。。。");
        return null;
    }

}
