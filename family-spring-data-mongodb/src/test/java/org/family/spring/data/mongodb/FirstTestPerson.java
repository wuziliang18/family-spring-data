package org.family.spring.data.mongodb;

import org.family.spring.data.mongodb.bean.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FirstTestPerson {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "mongodb.xml" });
		context.start();
		PersonRepository personRepository=context.getBean(PersonRepository.class);
		Person person=new Person();
		person.setFirtName("wu");
		person.setSecName("ziliang111");
		personRepository.save(person);
	}
}
