package org.example;

import org.example.bean.ServiceB;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class AopTest {

	public static void main(String[] args) {

		ServiceB target = new ServiceB();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		{
			proxyFactory.addAdvice(new MethodBeforeAdvice() {
				@Override
				public void before(Method method, Object[] args, Object target) throws Throwable {
					System.out.println("前置执行");
				}
			});

		}

		ServiceB proxy = (ServiceB) proxyFactory.getProxy();
		proxy.method01();
	}
}
