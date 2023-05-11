package com.example.springdemo.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.*;

@Slf4j
public class TaskThreadPoolExecutor extends ThreadPoolExecutor {

    private String threadPoolName = "default thread";

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
        log.info(String.valueOf(super.getCorePoolSize()));
        log.info(String.valueOf(super.getMaximumPoolSize()));
        log.info(String.valueOf(super.getKeepAliveTime(TimeUnit.NANOSECONDS)));
        log.info(String.valueOf(super.getQueue().size()));
        log.info(this.threadPoolName);
    }

    @Override
    public void execute(Runnable command) {
        if (command instanceof TaskRunnable) {
            super.execute(command);
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            TaskRunnable taskRunnable = new TaskRunnable(threadPoolName, command, requestAttributes);
            super.execute(taskRunnable);
        }
    }


}
