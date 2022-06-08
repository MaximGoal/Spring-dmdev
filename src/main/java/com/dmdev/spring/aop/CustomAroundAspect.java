package com.dmdev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class CustomAroundAspect {
    /* Advice */
    @Around("com.dmdev.spring.aop.CustomAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("AROUND before - invoked findByIdMethod in class {}, with id {}", service, id);
        try {
            var result = joinPoint.proceed();
            log.info("AROUND after returning - invoked findByIdMethod in class {}, with result {}", service, result);
            return result;
        } catch (Throwable throwable) {
            log.info("AROUND after throwing - invoked findByIdMethod in class {}, with throwable {}: {}", service, throwable.getClass(), throwable.getMessage());
            throw throwable;
        } finally {
            log.info("AROUND after (finally) - invoked findByIdMethod in class {}", service);
        }
    }
}
