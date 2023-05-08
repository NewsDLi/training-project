package com.example.springdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@RestController
@Slf4j
@RequestMapping("/async")
public class AsyncHandlerRequestController {

    @PostMapping("/doAsyncRequest")
    public String doAsyncRequest(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("进入controller方法");
        // 异步执行
        CompletableFuture.runAsync(() -> doHandler(file));
        return "success";
    }

    private void doHandler(MultipartFile file) {
        log.info("进入异步方法...");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // TODO 这里会报错
            // 1、由于是异步，请求周期已经结束，这里的MultipartFile数据已经销毁了
            // 2、解决方法，可以先将MultipartFile数据在异步方法执行之前读取出来file.getInputStream()然后在进行异步操作
            int read = file.getInputStream().read();
            log.info("123" + read);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("休眠结束...");
    }
}
