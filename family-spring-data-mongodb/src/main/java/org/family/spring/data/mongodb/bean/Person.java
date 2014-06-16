package org.family.spring.data.mongodb.bean;

import org.springframework.data.mongodb.core.mapping.Document;
//collection配制collection名称 如果不配置 为bean
@Document(collection="personCollection")
public class Person {
	private String firtName;
	private String secName;
	private int age;
	public String getFirtName() {
		return firtName;
	}
	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
