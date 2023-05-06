package com.example.springdemo.proxy.dynamic.cglib;



import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class OrderDemoServiceImplProxy implements MethodInterceptor {

    private Object target;

    public OrderDemoServiceImplProxy(Object object) {
        this.target = object;
    }

    public OrderDemoServiceImplProxy() {
    }

    /**
     * 创建一个代理对象
     *
     * @return
     */
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        Object o = enhancer.create();
        return o;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("支付之前。。。");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("支付之后。。。");
        return o1;
    }
}
