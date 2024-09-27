package org.example;

import org.example.bean.ServiceA;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;

public class ResolvableTypeTest {
	public static void main(String[] args) {
		ResolvableType resolvableType = ResolvableType.forClass(Handle.class);
		Type type = resolvableType.getType();
	}
}
