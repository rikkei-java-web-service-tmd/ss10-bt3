package com.re.rikkeistoreinventoryaop.aspect;

import jakarta.persistence.LockTimeoutException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InventoryPerformanceAspect {
    private static final Logger log = LoggerFactory.getLogger(InventoryPerformanceAspect.class);

    @Around("execution(public * com.re.rikkeistoreinventoryaop.service.InventoryService.*(..))")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startedAt = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable exception) {
            if (exception instanceof DataAccessException || exception instanceof LockTimeoutException) {
                log.error("Lỗi database tại hàm {}: {}", methodName, exception.getMessage(), exception);
            } else if (exception instanceof IllegalArgumentException) {
                log.warn("Lỗi nghiệp vụ tại hàm {}: {}", methodName, exception.getMessage());
            } else {
                log.error("Lỗi hệ thống tại hàm {}: {}", methodName, exception.getMessage(), exception);
            }
            throw exception;
        } finally {
            long elapsedTime = System.currentTimeMillis() - startedAt;
            log.info("Hàm {} chạy mất {} ms.", methodName, elapsedTime);
            if (elapsedTime > 500) {
                log.warn("[Performance Alert] Hàm {} quá chậm ({} ms).", methodName, elapsedTime);
            }
        }
    }
}
