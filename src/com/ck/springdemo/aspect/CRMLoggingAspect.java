package com.ck.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private static void printArgs(Object arg) {
        System.out.println("===> argument:" + arg);
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() { }

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());
    // setup pointcut declarations
    @Pointcut("execution(* com.ck.springdemo.controller.*.*(..))")
    private void forControllerPackage() { }

    @Pointcut("execution(* com.ck.springdemo.service.*.*(..))")
    private void forServicePackage() { }

    @Pointcut("execution(* com.ck.springdemo.dao.*.*(..))")
    private void forDaoPackage() { }

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        // display method we are calling
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("===> in @Before: calling method: " + method);

        // display the arguments to the method
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(CRMLoggingAspect::printArgs);
    }
    // add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()",
                    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        // display method we are returning from
        String method = joinPoint.getSignature().toShortString();
        myLogger.info("===> in @AfterReturning: from method: " + method);
        // display data returned
        myLogger.info("===> result: " + result);
    }
}
