package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice
 * 注解 @Aspect 将其标记为一个切面类
 */
@Aspect
@Component
@Slf4j
public class ApplicationAspect {

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("execution(public * org.example.bean.ServiceA.method()))")
    public void aspect() {
    }

    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受 JoinPoint 切入点对象,可以没有该参数
     */
    @Before("aspect()")
    public void before(JoinPoint joinPoint) {
		System.out.println("before---1");
    }

	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受 JoinPoint 切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before2(JoinPoint joinPoint) {
		System.out.println("before---2");
	}

}