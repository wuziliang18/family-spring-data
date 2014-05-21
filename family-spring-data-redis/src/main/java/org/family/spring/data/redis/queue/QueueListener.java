package org.family.spring.data.redis.queue;

import org.family.spring.data.redis.queue.interfaces.IRedisQueueListener;

public class QueueListener<String> implements IRedisQueueListener<String> {

	@Override
	public void onMessage(String value) {
		// TODO Auto-generated method stub
		 System.out.println(Thread.currentThread().getName()+  "接受数据"+value);  
	}

}
