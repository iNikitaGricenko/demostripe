package com.inikitagricenko.demo.stripe.config;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.*;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class AopConfiguration {

	private final IProductService productService;
	private final Validator validator;

	@Pointcut("@annotation(com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor)")
	public void performanceMonitor() { }

	@Pointcut("execution(public * com.inikitagricenko.demo.stripe.service..*(..)) || " +
			"execution(public * com.inikitagricenko.demo.stripe.persistence..*(..)) || " +
			"execution(public * com.inikitagricenko.demo.stripe.utils..*(..))")
	public void callAtAllPublicServices() { }

	@Pointcut("@annotation(com.inikitagricenko.demo.stripe.config.annotations.ProductValidation) && args(customerOrder,..)")
	public void orderItemsValidator(CustomerOrder customerOrder) { }

	@Pointcut("@annotation(com.inikitagricenko.demo.stripe.config.annotations.ProductValidation) && args(subscription,..)")
	public void productListValidator(Subscription subscription) { }

	@Pointcut("@annotation(com.inikitagricenko.demo.stripe.config.annotations.PaymentValidation) && args(customerOrder,..)")
	public void paymentValidator(CustomerOrder customerOrder) { }

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
		if (exception instanceof DefaultBackendException) {
			Throwable cause = exception.getCause();
			log.error("{} method {} exception occurs", className, methodName, cause);
		} else {
			log.error("{} method {} exception occurs", className, methodName, exception);
		}

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

	@Before(value = "paymentValidator(customerOrder)", argNames = "customerOrder")
	public void doValidatePayment(CustomerOrder customerOrder) {
		Payment payment = customerOrder.getPayment();
		if (payment == null) {
			throw new ValidationException("Payment must not be null");
		}
		Set<ConstraintViolation<Payment>> violations = validator.validate(payment);
		if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
	}

	@Before(value = "orderItemsValidator(customerOrder)", argNames = "customerOrder")
	public void doValidateOrderItems(CustomerOrder customerOrder) {
		validateOrderItems(customerOrder.getOrderItems());
	}

	@Before(value = "productListValidator(subscription)", argNames = "subscription")
	public void doValidateProductList(Subscription subscription) {
		validateProductList(subscription.getProductList());
	}

	@Async
	protected void validateOrderItems(Collection<OrderItem> orderItems) {
		List<Long> productIds = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
		if (!productService.exists(productIds)) {
			throw new EntityNotFoundException("Product not found");
		}
	}

	@Async
	protected void validateProductList(Collection<Product> productList) {
		List<Long> productIds = productList.stream().map(Product::getId).collect(Collectors.toList());
		if (!productService.exists(productIds)) {
			throw new EntityNotFoundException("Product not found");
		}
	}

}
