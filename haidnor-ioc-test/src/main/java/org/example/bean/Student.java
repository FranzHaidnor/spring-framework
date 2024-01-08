package org.example.bean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

	@Autowired
	private Dog dog;

}
