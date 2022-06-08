package com.dmdev.spring.aop;

import com.dmdev.spring.validation.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Aspect
@Component
@Order(1)
public class CustomAspect {

    /*
    this - check AOP proxy class type
    target - check target object class type
    */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

    /*
    @annotation - check annotation on the method level
    */
    @Pointcut("com.dmdev.spring.aop.CommonPointcut.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
    @arg - check param type in the method level
    * - any param type
    .. - 0+ any param types
    */
    @Pointcut("com.dmdev.spring.aop.CommonPointcut.isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam() {
    }

    /*
    @arg - check annotation on the param type
    * - any param type
    .. - 0+ any param types
    */
    @Pointcut("com.dmdev.spring.aop.CommonPointcut.isControllerLayer() && @args(com.dmdev.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /*
    bean - check bean name
     */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    /*
    execution(modifiers-pattern? return-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     */
    @Pointcut("execution(public Long com.dmdev.spring.service.*Service.findById(*) )")
    public void anyFindByIdServiceMethod() {
    }

    /* Advice */
    @Before(value = "anyFindByIdServiceMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                          Object id,
                          Object service,
                          Object serviceProxy,
                          Transactional transactional) {
        log.info("before - invoked findByIdMethod in class {}, with id {}", service, id);
    }

    /* Advice */
    @AfterReturning(value = "anyFindByIdServiceMethod() &&" +
            "target(service)",
            returning = "result")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("after returning - invoked findByIdMethod in class {}, with result {}", service, result);
    }

    /* Advice */
    @AfterThrowing(value = "anyFindByIdServiceMethod() &&" +
            "target(service)",
            throwing = "throwable")
    public void addLoggingAfterThrowing(Object service, Throwable throwable) {
        log.info("after throwing - invoked findByIdMethod in class {}, whith trowable {}: {}", service, throwable.getClass(), throwable.getMessage());
    }

    /* Advice */
    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("after (finally) - invoked findByIdMethod in class {}", service);
    }
}
