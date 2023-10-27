package org.example;

import org.example.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 从 XML 配置文件中注册 bean
 */
public class TestGenericXmlApplicationContext {
	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext("beans.xml");
		Student obj2 = (Student) context.getBean("student");
		System.out.println(obj2);
	}
}