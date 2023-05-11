package com.example.springdemo.aop.log;

import com.alibaba.fastjson.JSONObject;
import com.example.springdemo.annotation.CreateDCLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {


//    @Before(value = "@annotation(dcLog)")
//    public void before(JoinPoint point, CreateDCLog dcLog) {
//        log.info("服务{}调用开始,请求参数:{}", dcLog.value(), JSONObject.toJSONString(point.getArgs()));
//    }

    @Around(value = "@annotation(dcLog)")
    public Object around(ProceedingJoinPoint point, CreateDCLog dcLog) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        String value = dcLog.value();
        if (StringUtils.isBlank(value)) {
            String methodName = point.getSignature().getName();
            value = String.format("%s.%s", aClass.getSimpleName(), methodName);
        }
        // 这里每次需要获取logger，性能损耗是否能接受，待测试
        Logger logger = LoggerFactory.getLogger(aClass.getName());
        Object result = null;
        long start = System.currentTimeMillis();
        logger.info("接口{}调用开始,request:{}", value, JSONObject.toJSONString(point.getArgs()));
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            logger.error("接口{}异常:", value, throwable);
            throw new Throwable();
        } finally {
            long end = System.currentTimeMillis();
            logger.info("接口{}调用结束,response:{},耗时:{}ms", value, JSONObject.toJSONString(result), (end - start));
        }
        return result;
    }

    //设置rvt为返回参数
//    @AfterReturning(value = "@annotation(dcLog)", returning = "rvt")
//    public void afterReturning(JoinPoint point, CreateDCLog dcLog, Object rvt) {
//        log.info("服务{}调用结束，response：{}", dcLog.value(), JSONObject.toJSONString(rvt));
//    }
}
