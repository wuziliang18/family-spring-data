package org.family.spring.data.redis.pub;

import java.io.Serializable;

import javax.annotation.Resource;

import org.family.spring.data.redis.pub.interfaces.ISendMessage;
import org.springframework.data.redis.core.RedisTemplate;

public class SendMessageImpl implements ISendMessage {
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate; 
	@Override
	public void sendMesage(String channel,String msg) {
		// TODO Auto-generated method stub
		redisTemplate.convertAndSend(channel, msg);
	}

}
