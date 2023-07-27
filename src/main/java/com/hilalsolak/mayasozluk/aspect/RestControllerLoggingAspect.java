package com.hilalsolak.mayasozluk.aspect;
import com.hilalsolak.mayasozluk.service.LoggerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
public class RestControllerLoggingAspect {
    private final LoggerService service;
    private static final Logger log = LoggerFactory.getLogger(RestControllerLoggingAspect.class);

    public RestControllerLoggingAspect(LoggerService service) {
        this.service = service;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String clientId = RequestContextHolder.currentRequestAttributes().getSessionId();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String activityType = "Class: " + className + " Method: " + methodName + " is called";
        service.createLogger(clientId, activityType);

        Object returnedValue = joinPoint.proceed();
        log.info(activityType + "\n");

        return returnedValue;
    }
}
