package com.example.springdemo.service.strategy.impl;

import com.example.springdemo.service.strategy.SendMessageStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DingTalkSendMessageStrategy implements SendMessageStrategyService {

    @Override
    public boolean sendMessage(String message) {
        log.info("dingtalk send message.{}", message);
        return true;
    }

    @Override
    public String getType() {
        return "DingTalk";
    }
}
