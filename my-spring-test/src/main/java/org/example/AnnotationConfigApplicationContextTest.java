package org.example;

import org.example.bean.*;
import config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class AnnotationConfigApplicationContextTest {

	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("config");

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		serviceA.method();

		// 销毁Spring上下文
		context.close();
	}

	public static void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// context.addBeanFactoryPostProcessor(); // 提前手动添加一些自定义的 BeanFactoryPostProcessor
		context.register(AppConfiguration.class);
		context.refresh();

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		serviceA.method();
	}

}