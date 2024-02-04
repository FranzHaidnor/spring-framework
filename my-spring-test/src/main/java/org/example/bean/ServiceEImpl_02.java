package org.example.bean;

import org.springframework.stereotype.Service;

@Service
public class ServiceEImpl_02 implements IServiceE {

	@Override
	public void method() {
		System.out.println("ServiceEImpl_02 method");
	}

}
