package com.example.springdemo.controller;

import com.example.springdemo.service.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SendMessageStrategyController {

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message, @RequestParam("type") String type) {
        String result = sendMessageService.sendMessage(message, type);
        return result;
    }

    @GetMapping("/sendMessage2")
    public String sendMessage2(@RequestParam("message") String message, @RequestParam("type") String type) {
        String result = sendMessageService.sendMessage(message, type);
        sendMessage3(result);
        return result;
    }

    private void sendMessage3(String result) {
        System.out.println("==============");
        System.out.println(result);
    }
}
