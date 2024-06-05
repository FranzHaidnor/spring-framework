package org.example.factorybean;

import org.example.bean.ServiceA;
import org.springframework.beans.factory.FactoryBean;

//@Component
public class ServiceCFactoryBean implements FactoryBean<ServiceA> {

	@Override
	public ServiceA getObject() {
		System.out.println("调用 ServiceCFactoryBean 创建 ServiceC 实例");
		return new ServiceA();
	}

	@Override
	public Class<?> getObjectType() {
		return ServiceA.class;
	}

}