package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ServiceA implements IServiceA {

	@Autowired
	private ServiceB serviceB;


	@Override
	public void method() {
		serviceB.method01();
	}

}
