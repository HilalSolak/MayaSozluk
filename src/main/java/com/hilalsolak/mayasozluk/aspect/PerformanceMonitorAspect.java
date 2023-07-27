package com.hilalsolak.mayasozluk.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {
    private static final Logger log = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object monitorExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed();
        } finally {
            long duration = calculateDuration(startTime);

            if (isDurationExceeded(duration)) {
                logWarning(joinPoint, duration);
            }
        }

        return result;
    }

    private long calculateDuration(long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private boolean isDurationExceeded(long duration) {
        return duration > 1000;
    }

    private void logWarning(ProceedingJoinPoint joinPoint, long duration) {
        String message = String.format("%s.%s took longer than 1 seconds to execute. Duration: %d ms\n",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                duration);
        log.warn(message);
    }
}