package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {

	@Autowired
	@Qualifier("serviceEImpl_01")
	private IServiceE serviceE;

	public void method() {
		serviceE.method();
	}

}
