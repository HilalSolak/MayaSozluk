package com.hilalsolak.mayasozluk.utils;

import com.hilalsolak.mayasozluk.service.LoggerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
public class LoggingAspect {
    private final LoggerService loggerService;

    public LoggingAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String clientId =  RequestContextHolder.currentRequestAttributes().getSessionId();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String activityType = "Class: "+ className +" Method: " + methodName + " is called";
        loggerService.createLogger(clientId,activityType);

        Object returnedValue = joinPoint.proceed();

        return returnedValue;


    }

}
