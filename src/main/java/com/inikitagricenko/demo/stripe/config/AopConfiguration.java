package com.inikitagricenko.demo.stripe.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Pointcut("@annotation(com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor)")
	public void performanceMonitor() { }

	@Pointcut("execution(public * com.inikitagricenko.demo.stripe.service..*(..)) || " +
			"execution(public * com.inikitagricenko.demo.stripe.persistence..*(..)) || " +
			"execution(public * com.inikitagricenko.demo.stripe.utils..*(..))")
	public void callAtAllPublicServices() { }

	@Bean
	public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
		return new PerformanceMonitorInterceptor(true);
	}

	@Bean
	public Advisor performanceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("com.inikitagricenko.demo.stripe.config.AopConfiguration.performanceMonitor()");
		return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
	}

	@AfterThrowing(value = "callAtAllPublicServices()", throwing = "exception")
	public void logOnServiceThrow(JoinPoint joinPoint, Exception exception) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		//Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		//Log method that throw exception
		log.error("{} method {} exception occurs", className, methodName, exception);
	}

	@Around("performanceMonitor()")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		//Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();
		//Measure method execution time
		try {
			stopWatch.start();
			return proceedingJoinPoint.proceed();
		} finally {
			stopWatch.stop();
			//Log method execution time
			log.info("{}.{} Execution time :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());
		}
	}

}
