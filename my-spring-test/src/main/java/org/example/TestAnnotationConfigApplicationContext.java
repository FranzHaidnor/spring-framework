package org.example;

import org.example.bean.ServiceA;
import org.example.config.SpringAsyncConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class TestAnnotationConfigApplicationContext {
	public static void main(String[] args) {

		// AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext("org.example");

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SpringAsyncConfig.class);
		context.refresh();

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		serviceA.invokeServiceBMethod();

	}
}