package com.zcb.minimallcore.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * @author zcbin
 * @title: LogAspect
 * @projectName minimall
 * @description: 日志
 * @date 2019/8/26 17:26
 */
@Aspect //切面
@Component
public class LogAspect {
    private static final Logger LOGGER = LogManager.getLogger();

    //设置切入点：这里直接拦截被@RestController注解的类
    //@Pointcut("execution(* com.zcb.minimalladminapi.controller.*.*(..))")
    @Pointcut("@annotation(com.zcb.minimallcore.advice.Log)") //切入点
    public void pointcut() {
    }

    //    @Pointcut("execution(* com.zcb.minimalladminapi.controller.AdminAuthController(..))")
//    public void p(){}
    /*@Before(value = "pointcut()")
    public void doBefore() {
        System.out.println("----------------------------------------before");
    }*/
    @Around(value = "pointcut()&&@annotation(log)") //通知
    public Object doAround(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        long beginTime = System.currentTimeMillis();//1、开始时间
        //利用RequestContextHolder获取requst对象
        ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String uri = requestAttr.getRequest().getRequestURI();
        LOGGER.info("Log description：{}", log.desc());
        LOGGER.info("Start timing: {}, URI: {}", LocalDateTime.now(), uri);
        //访问目标方法的参数 可动态改变参数值
        Object[] args = joinPoint.getArgs();
        //方法名获取
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Request method：{}.{}(), Request parameter: {}", log.clazz().getName(), methodName, Arrays.toString(args));
        Object obj = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        LOGGER.info("End timing: {},  URI: {}, Time-consuming：{}ms", new Date(), uri, endTime - beginTime);

        return obj;
    }
}
