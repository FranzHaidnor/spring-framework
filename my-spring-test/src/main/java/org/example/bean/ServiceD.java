package org.example.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceD {

	@Autowired
	private ServiceC serviceC;

	public void method() {
		System.out.println("invoke ServiceD method");
	}
}
