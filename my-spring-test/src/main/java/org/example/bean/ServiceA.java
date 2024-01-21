package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {

	@Autowired
	private ServiceC serviceC;

}
