package com.zcb.minimallcore.advice;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zcbin
 * @title: SystemLog
 * @projectName minimall
 * @description: 日志注解
 * @date 2019/8/26 17:19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})//只能在方法上使用此注解
public @interface Log {
    /**
     * 日志描述
     *
     * @return
     */
    @AliasFor("desc")
    String desc() default "";

    Class clazz() default Object.class;

    /**
     * 是否不记录日志
     *
     * @return
     */
    boolean ignore() default false;
}
