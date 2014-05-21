package org.family.spring.data.redis.sub;

import org.family.spring.data.redis.queue.RedisQueue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestQueue {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:app-redis-queue.xml");  
        RedisQueue<String> redisQueue = (RedisQueue)context.getBean("jedisQueue");  
        for(int i=0;i<10000;i++){
        	redisQueue.pushFromHead("测试信息"+i);  
        }
	}
}
