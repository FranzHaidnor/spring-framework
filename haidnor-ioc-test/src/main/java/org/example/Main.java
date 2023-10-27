package org.example;

import org.example.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext("beans.xml");
		Student obj2 = (Student) context.getBean("student");
		System.out.println(obj2);
	}
}