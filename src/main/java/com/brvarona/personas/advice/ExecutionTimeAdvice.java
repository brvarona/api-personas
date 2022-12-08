package com.brvarona.personas.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(com.brvarona.personas.advice.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();        
        log.info(new StringBuilder("Clase: ").append(point.getSignature().getDeclaringTypeName())
        				.append(". Metodo: ").append(point.getSignature().getName())
        				.append(". Tiempo de ejecucion: ").append(endtime-startTime).append("ms")
        			.toString());
        
        return object;
    }
}