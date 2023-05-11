package com.example.springdemo.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TaskRunnable implements Runnable {

    private String taskName;

    private Runnable runnable;

    private ConcurrentHashMap<String, String> requestHeaderMap;

    private RequestAttributes requestAttributes;

    public TaskRunnable(String taskName, Runnable runnable) {
        this.taskName = taskName;
        this.runnable = runnable;
    }

    public TaskRunnable(String taskName, Runnable runnable, ConcurrentHashMap<String, String> requestHeaderMap) {
        this.taskName = taskName;
        this.runnable = runnable;
        this.requestHeaderMap = requestHeaderMap;
    }

    public TaskRunnable(String taskName, Runnable runnable, RequestAttributes requestAttributes) {
        this.taskName = taskName;
        this.runnable = runnable;
        this.requestAttributes = requestAttributes;
    }

    @Override
    public void run() {
        try {
//            if (this.requestHeaderMap != null) {
//                for (Map.Entry<String, String> entry : this.requestHeaderMap.entrySet()) {
//                    SystemContext.put(entry.getKey(), entry.getValue());
//                }
//            }
            // 新线程中设置请求属性
            if (null != requestAttributes) {
                RequestContextHolder.setRequestAttributes(requestAttributes);
            }
            runnable.run();
        } finally {
//            if (this.requestHeaderMap != null) {
//                SystemContext.clean();
//            }
            if (null != requestAttributes) {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}
