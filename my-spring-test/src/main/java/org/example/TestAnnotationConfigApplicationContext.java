package org.example;

import org.example.bean.ServiceA;
import org.example.config.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class TestAnnotationConfigApplicationContext {

	public static void main(String[] args) {

		 AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext("org.example");

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SpringConfiguration.class);
		context.refresh();

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");

	}
}