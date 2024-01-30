package org.example.bean;


import org.springframework.stereotype.Component;

//@Component
public class ServiceA {

	private ServiceD serviceD;

	public ServiceA(ServiceD serviceD) {
		this.serviceD = serviceD;
	}
}
