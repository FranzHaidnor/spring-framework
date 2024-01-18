package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {

	@Autowired
	@Lazy
	private ServiceB serviceB;

	@Async
	public void asyncMethod() {
		System.out.println("invoke async method");
	}

	public void invokeServiceBMethod() {
		serviceB.method01();
	}

}
