package org.example.bean;

import org.springframework.stereotype.Service;

@Service
public class ServiceEImpl_01 implements IServiceE {

	@Override
	public void method() {
		System.out.println("ServiceEImpl_01 method");
	}

}
