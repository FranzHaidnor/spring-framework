package org.example;

import org.example.bean.ServiceA;
import org.example.bean.ServiceC;
import org.example.bean.ServiceD;
import org.example.factorybean.ServiceCFactoryBean;
import org.example.config.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class TestAnnotationConfigApplicationContext {

	public static void main(String[] args) {
		test3();
	}

	public static void test1() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");
		ServiceA serviceA = (ServiceA) context.getBean("serviceA");
		System.out.println(serviceA);
	}

	public static void test2() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SpringConfiguration.class);
		context.refresh();

		ServiceC serviceC = context.getBean("serviceC", ServiceC.class);
		System.out.println(serviceC);

		ServiceCFactoryBean serviceCFactoryBean = context.getBean("&serviceC", ServiceCFactoryBean.class);
		System.out.println(serviceCFactoryBean);

	}

	public static void test3() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");
		ServiceD serviceD = (ServiceD) context.getBean("serviceD");
		System.out.println(serviceD);
	}

	public static void test4() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");
		// 根据类型获取
		ServiceC serviceC = context.getBean(ServiceC.class);
		// 根据名称获取,报错
//		ServiceC serviceC = context.getBean("serviceC", ServiceC.class);
		System.out.println(serviceC);
	}
}