package org.example.factorybean;

import org.example.bean.ServiceC;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ServiceCFactoryBean implements FactoryBean<ServiceC> {

	@Override
	public ServiceC getObject() {
		System.out.println("调用 ServiceCFactoryBean 创建 ServiceC 实例");
		return new ServiceC();
	}

	@Override
	public Class<?> getObjectType() {
		return ServiceC.class;
	}

}