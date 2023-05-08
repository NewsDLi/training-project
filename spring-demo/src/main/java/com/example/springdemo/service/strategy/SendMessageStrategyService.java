package com.example.springdemo.service.strategy;

public interface SendMessageStrategyService {

    /**
     * 消息发送方法
     *
     * @param message
     * @return
     */
    boolean sendMessage(String message);

    /**
     * 获取策略类型
     *
     * @return
     */
    String getType();
}
