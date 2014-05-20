package org.family.spring.data.redis;

import java.util.Date;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.family.spring.data.redis.pub.interfaces.ISendMessage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSub {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-redis-sub.xml" });
		context.start();
		System.out.println("加载完毕");
		while (true) {
			System.out.println("等待current time: " + new Date());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
