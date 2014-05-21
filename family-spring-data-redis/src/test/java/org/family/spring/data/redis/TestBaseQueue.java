package org.family.spring.data.redis;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBaseQueue {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		context.start();
		RedisServiceImpl redisServer = context.getBean(RedisServiceImpl.class);
		while(true){
			String result=redisServer.outQueue("testqueue");
			if(result!=null){
				System.out.println("2>>"+result);;
			}
		}
	}
}
