package com.example.springdemo.controller;

import com.example.springdemo.annotation.CreateDCLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("log")
public class LogController {

    @CreateDCLog("LogController.logController")
    @GetMapping("/sendMessage")
    public String logController(@RequestParam("message") String message, @RequestParam("type") String type) {
        String result = "结束";
        return result;
    }

    @CreateDCLog()
    @GetMapping("/voidSendMessage")
    public void logVoidController(@RequestParam("message") String message, @RequestParam("type") String type) throws Exception {
        throw new Exception("异常");
    }
}
