package org.example;

import org.example.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解注册bean
 */
public class TestAnnotationConfigApplicationContext {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("org.example");
		Student obj2 = (Student) context.getBean("student");
		System.out.println(obj2);
	}
}