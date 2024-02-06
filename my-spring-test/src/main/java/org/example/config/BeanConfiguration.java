package org.example.config;

import org.example.bean.ServiceF;
import org.springframework.context.annotation.Bean;

public class BeanConfiguration {

	@Bean
	public ServiceF serviceF() {
		return new ServiceF();
	}

}
