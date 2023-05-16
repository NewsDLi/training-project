package com.example.springdemo.aop.log;

import com.alibaba.fastjson.JSONObject;
import com.example.springdemo.annotation.CreateDCLog;
import com.example.springdemo.annotation.LogLevel;
import com.example.springdemo.annotation.LogLevelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

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
        Signature signature = point.getSignature();
        String methodName = signature.getName();
        String methodInfo = String.format("%s.%s", aClass.getSimpleName(), methodName);
        MethodSignature ms = (MethodSignature)point.getSignature();
        Method targetMethod = aClass.getDeclaredMethod(signature.getName(), ms.getParameterTypes());
        LogLevel annotation = targetMethod.getAnnotation(LogLevel.class);
        LogLevelType levelType = LogLevelType.INFO;
        if (null != annotation) {
            levelType = annotation.value();
        }
        // 这里每次需要获取logger，性能损耗是否能接受，待测试
        // Logger logger = LoggerFactory.getLogger(aClass.getName());
        Object result = null;
        long start = System.currentTimeMillis();
        printLog("{}接口调用开始,request:{}", levelType, methodInfo, JSONObject.toJSONString(point.getArgs()));
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            log.error("{}接口异常:", methodInfo, throwable);
            throw new Throwable();
        } finally {
            long end = System.currentTimeMillis();
            printLog("{}接口调用结束,response:{},耗时:{}ms", levelType, methodInfo, JSONObject.toJSONString(result), (end - start));
        }
        return result;
    }

    private void printLog(String format, LogLevelType levelType, Object... arguments) {
        if (LogLevelType.INFO.equals(levelType)) {
            log.info(format, arguments);
        } else if (LogLevelType.WARN.equals(levelType)) {
            log.warn(format, arguments);
        } else if (LogLevelType.DEBUG.equals(levelType)) {
            log.debug(format, arguments);
        } else if (LogLevelType.ERROR.equals(levelType)) {
            log.error(format, arguments);
        }
    }

}
