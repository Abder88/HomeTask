package com.githubapi.hometask.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class ExecutionTimeAspect {

  /**
   * Pointcut:
   * Matches any public method in the com.githubapi.hometask.controller package.
   */
  @Around("execution(* com.githubapi.hometask.controller..*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      return joinPoint.proceed();
    } finally {
      long end = System.currentTimeMillis();
      log.info("Execution time of {} is {} ms", joinPoint.getSignature(), (end - start));
    }
  }

}
