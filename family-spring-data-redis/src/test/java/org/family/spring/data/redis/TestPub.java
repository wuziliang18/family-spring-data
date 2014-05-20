package org.family.spring.data.redis;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.family.spring.data.redis.pub.interfaces.ISendMessage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPub {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-redis-pub.xml" });
		context.start();
		System.out.println("加载完毕");
		ISendMessage sendMessage = context.getBean(ISendMessage.class);
		for(int i=100;i<200;i++){
			String msg="测试信息"+i;
			System.out.println("发送信息:"+msg);
			sendMessage.sendMesage("chanel1", msg);
		}
	}
}
