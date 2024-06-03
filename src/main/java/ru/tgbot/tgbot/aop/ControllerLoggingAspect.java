package ru.tgbot.tgbot.aop;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ControllerLoggingAspect {

    @Pointcut("execution(public * ru.tgbot.tgbot.controller..*(..))")
    public void callController() { }

    @Before("callController()")
    public void beforeCallController(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .toList();

        log.info("Call {} with args {}", joinPoint.getSignature().getName(), args);
    }

    @AfterReturning(value = "callController()", returning ="object")
    public void afterCallController(JoinPoint joinPoint, ResponseEntity<?> object) {
        log.info("Call {} with return {}", joinPoint.getSignature().getName(), object.getBody());
    }
}