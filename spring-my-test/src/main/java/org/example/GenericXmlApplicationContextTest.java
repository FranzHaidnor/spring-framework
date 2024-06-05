package org.example;

import org.example.bean.OrderService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 从 XML 配置文件中注册 bean
 */
public class GenericXmlApplicationContextTest {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("beans.xml");
//		Student obj2 = (Student) context.getBean("student");

		BeanDefinition beanDefinition = new RootBeanDefinition(OrderService.class);
		context.registerBeanDefinition("orderService", beanDefinition);


		OrderService orderService = context.getBean("orderService", OrderService.class);
		System.out.println(orderService);
	}
}