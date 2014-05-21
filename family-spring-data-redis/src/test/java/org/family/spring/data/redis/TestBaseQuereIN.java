package org.family.spring.data.redis;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBaseQuereIN {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		context.start();
		RedisServiceImpl redisServer = context.getBean(RedisServiceImpl.class);
		for(int i=0;i<100;i++){
			redisServer.inQuere("testqueue", "测试队列"+i);
		}
	}
}
