package ru.tgbot.tgbot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@Aspect
public class ServiceLoggingAspect {


    @Pointcut("execution(* ru.tgbot.tgbot.service..*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing service method: " + joinPoint.getSignature().getName());
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Method executed: " + joinPoint.getSignature().getName());
        log.info("Return value: " + result);
    }
}
