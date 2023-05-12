package com.example.springdemo.aop.log;

import com.alibaba.fastjson.JSONObject;
import com.example.springdemo.annotation.CreateDCLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    // 这里还可以切FeignClient   @Pointcut("@annotation(given) || @annotation(then) || @annotation(when) ")
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut(){};


    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        String value = String.format("%s.%s", aClass.getSimpleName(), methodName);
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

}
