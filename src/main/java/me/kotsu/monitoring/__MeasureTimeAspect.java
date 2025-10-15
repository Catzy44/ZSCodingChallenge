//package me.kotsu.monitoring;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Aspect
//public class __MeasureTimeAspect {
//	private static final Logger logger = LoggerFactory.getLogger(__MeasureTimeAspect.class);
//
//    @Around("@annotation(measureTime)")
//    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, __MeasureTime measureTime) throws Throwable {
//        long start = System.currentTimeMillis();
//
//        Object result = joinPoint.proceed();
//
//        long end = System.currentTimeMillis();
//        String tag = measureTime.value().isEmpty()
//                ? joinPoint.getSignature().toShortString()
//                : measureTime.value();
//
//        logger.info("Method {} executed in {} ms", tag, (end - start));
//        return result;
//    }
//}