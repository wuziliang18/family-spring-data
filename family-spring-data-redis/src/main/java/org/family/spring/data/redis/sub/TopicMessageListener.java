package org.family.spring.data.redis.sub;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

public class TopicMessageListener implements MessageListener {
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate; 
	@Override
	public void onMessage(Message message, byte[] pattern) {
		//注意方法内多线程 
		// TODO Auto-generated method stub
		System.out.println("接受信息:>>>>>"+message.toString());
		System.out.println("接受渠道:>>>>>"+new String(message.getChannel()));
		System.out.println("解析byte>>"+new String(pattern));
	}

}
