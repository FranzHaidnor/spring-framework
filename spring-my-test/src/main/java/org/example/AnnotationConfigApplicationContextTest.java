package org.example;

import config.AppConfiguration;
import org.example.bean.ServiceA;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class AnnotationConfigApplicationContextTest {

	public static void main(String[] args) {
		test2();
	}

	/**
	 * 从包路径扫描
	 */
	public static void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("config");

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		serviceA.method();

		// 销毁Spring上下文
		context.close();
	}

	/**
	 * 使用配置类扫描
	 */
	public static void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 提前手动添加一些自定义的 BeanFactoryPostProcessor
//		 context.addBeanFactoryPostProcessor();
		context.register(AppConfiguration.class);
		context.refresh();

		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		serviceA.method();
	}

}