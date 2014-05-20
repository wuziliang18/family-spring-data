package org.family.spring.data.redis.pub.interfaces;

public interface ISendMessage {
	public void sendMesage(String channel,String msg);
}
