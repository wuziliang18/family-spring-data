package org.family.spring.data.redis.queue.interfaces;

public interface IRedisQueueListener<T> {
	public void onMessage(T value); 
}
