package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {

	@Autowired
	private ServiceB serviceB;

	public void method() {
		serviceB.method01();
	}

}
