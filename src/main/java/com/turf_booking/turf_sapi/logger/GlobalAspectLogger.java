package com.turf_booking.turf_sapi.logger;

import com.turf_booking.turf_sapi.model.ApiResponse;
import com.turf_booking.turf_sapi.model.Turf;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Log4j2
@Component
public class GlobalAspectLogger {

    String prefix = GlobalLog.prefix;

    @Pointcut("execution(* com.turf_booking.turf_sapi.*.*.*(..))")
    public void logPointCut(){}


    @Before(value = "execution(* com.turf_booking.turf_sapi.controller.*.*(..))")
    public void beforeController(JoinPoint joinPoint){
        log.info(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName()); ; // + "::" + joinPoint.getSignature().getName());
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinPoint){
        log.debug(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName() + "::Called"); ; // + "::" + joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(* com.turf_booking.turf_sapi.error.*.*(..))",returning = "apiResponse")
    public void afterException(JoinPoint joinPoint, ResponseEntity<ApiResponse<String>> apiResponse){
        log.error(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName() + "::ERROR::CODE:" + apiResponse.getBody().getStatusCode() +"::DESCRIPTION:" + apiResponse.getBody().getApiError().getErrorMessage());
    }

    @AfterReturning(value = "execution(* com.turf_booking.turf_sapi.controller.*.*(..))",returning = "apiResponse")
    public void afterTurf(JoinPoint joinPoint, ResponseEntity<ApiResponse<Turf>> apiResponse){
        log.info(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName() + "::Response Payload: " + apiResponse.getBody().getPayload().toString());
    }

    @AfterReturning(value = "execution(* com.turf_booking.turf_sapi.controller.*.*(..))",returning = "apiResponse")
    public void afterTurfList(JoinPoint joinPoint, ResponseEntity<ApiResponse<List<Turf>>> apiResponse){
        log.info(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName() + "::Response Payload: " + apiResponse.getBody().getPayload().toString());
    }

    @AfterReturning(value = "execution(* com.turf_booking.turf_sapi.controller.*.*(..))",returning = "apiResponse")
    public void afterString(JoinPoint joinPoint, ResponseEntity<ApiResponse<String>> apiResponse){
        log.info(prefix + joinPoint.getSignature().getDeclaringType().getSimpleName() + "::" + joinPoint.getSignature().getName() + "::Response Payload: " + apiResponse.getBody().getPayload().toString());
    }


}
