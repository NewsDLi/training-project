package com.example.springdemo.proxy;

import com.example.springdemo.proxy.dynamic.cglib.OrderDemoServiceImpl;
import com.example.springdemo.proxy.dynamic.cglib.OrderDemoServiceImplProxy;
import com.example.springdemo.proxy.dynamic.jdk.PaymentService;
import com.example.springdemo.proxy.dynamic.jdk.PaymentServiceImpl;
import com.example.springdemo.proxy.dynamic.jdk.PaymentServiceImplProxy;
import com.example.springdemo.proxy.stati.OrderServiceImplProxy;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;


public class ProxyDemoTest {

    /**
     * 静态代理
     */
    @Test
    public void staticProxy() {
        // OrderServiceImplProxy 代理对象
        OrderServiceImplProxy orderServiceImplProxy = new OrderServiceImplProxy();
        String pay = orderServiceImplProxy.pay();
        System.out.println(pay);
    }

    /**
     * JDk动态代理
     * 1、创建目标接口 PaymentService
     * 2、创建目标接口实现类 PaymentServiceImpl （代理类）
     * 3、创建实现类的代理对象 PaymentServiceImplProxy 实现 InvocationHandler接口 重写 invoke 方法
     */
    @Test
    public void jdkProxy() {
        InvocationHandler invocationHandler = new PaymentServiceImplProxy(new PaymentServiceImpl());
        PaymentService paymentService = (PaymentService) Proxy.newProxyInstance(PaymentService.class.getClassLoader(), new Class[]{PaymentService.class}, invocationHandler);
        boolean b = paymentService.payMoney(111);
        System.out.println("支付之后。。。。");
    }

    @Test
    public void jdkProxy02() {
        PaymentService paymentServiceProxy = new PaymentServiceImpl();
        // 创建代理类
        PaymentService paymentService = (PaymentService) Proxy.newProxyInstance(PaymentService.class.getClassLoader(), new Class[]{PaymentService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("支付之前");
                return method.invoke(paymentServiceProxy, args);
            }
        });
        paymentService.payMoney(1111);
        System.out.println("支付之后。。。。");
    }

    /**
     * CGLIB动态代理
     * 1、创建目标接口
     * 2、创建目标接口实现类，即代理类
     * 3、创建一个拦截器实现 MethodInterceptor 接口
     * 4、使用Enhancer设置要执行的代理类作为父类
     * 5、设置实现 MethodInterceptor 接口的回调对象
     * 6、enhancer创建代理对象
     * 7、方法调用
     */
    @Test
    public void cglibProxy() {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(OrderDemoServiceImpl.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new OrderDemoServiceImplProxy());
        // 创建代理对象
        OrderDemoServiceImpl userService = (OrderDemoServiceImpl)enhancer.create();
        // 通过代理对象调用目标方法
        String s = userService.payOrder(111);
        System.out.println("支付之后。。。");
    }


    @Test
    public void cglibProxy02() {
        OrderDemoServiceImpl orderDemoServiceImpl = new OrderDemoServiceImpl();
        OrderDemoServiceImplProxy orderDemoServiceImplProxy = new OrderDemoServiceImplProxy(orderDemoServiceImpl);
        OrderDemoServiceImpl proxy = (OrderDemoServiceImpl) orderDemoServiceImplProxy.getProxy();
        String s = proxy.payOrder(111);
    }

}
