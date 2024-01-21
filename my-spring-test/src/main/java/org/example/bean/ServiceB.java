package org.example.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope("prototype")
public class ServiceB {

	@Autowired
	private ServiceA serviceA;

	public void method01() {
		System.out.println("invoke ServiceB method_01");
	}
}
