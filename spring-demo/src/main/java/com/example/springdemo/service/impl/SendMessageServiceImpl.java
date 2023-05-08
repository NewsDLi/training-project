package com.example.springdemo.service.impl;

import com.example.springdemo.service.SendMessageService;
import com.example.springdemo.service.strategy.SendMessageStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private List<SendMessageStrategyService> sendMessageStrategyServices;

    /**
     * 消息发送
     *
     * @param message 消息内容
     * @param type 消息类型 DingTalk、QQ、weChart
     * @return
     */
    @Override
    public String sendMessage(String message, String type) {
        Map<String, SendMessageStrategyService> sendMessageStrategyMap = sendMessageStrategyServices.stream().filter(s -> s.getType().equals(type)).collect(Collectors.toMap(k -> k.getType(), v -> v, (v1, v2) -> v2));
        SendMessageStrategyService sendMessageStrategyService = sendMessageStrategyMap.get(type);
        if (null == sendMessageStrategyService) {
            return "fail";
        }
        boolean result = sendMessageStrategyService.sendMessage(message);
        return String.valueOf(result);
    }
}
