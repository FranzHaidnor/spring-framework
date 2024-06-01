package org.example.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceB {

	@Autowired
	private ServiceA serviceA;

	public void method01() {
		System.out.println("invoke ServiceB method_01");
	}
}
