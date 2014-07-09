package org.family.spring.data.redis.base;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.family.spring.data.redis.base.interfaces.IRedisService;
import org.family.spring.data.redis.support.RedisUtil;
import org.family.spring.data.redis.support.SaveRedisBean;
import org.family.spring.data.redis.support.TestBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


/**
 * 对redis操作的service
 * 
 * @author wuzl
 * 
 */
public class RedisServiceImpl implements IRedisService {
	@Resource
	private StringRedisTemplate redisTemplate;

	private RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 保存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final Object value) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(getRedisSerializer().serialize(key),
						RedisUtil.getBytesFromObject(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 保存字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (value == null) {
			throw new RuntimeException("value不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(getRedisSerializer().serialize(key),
						getRedisSerializer().serialize(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 保存一个字符串一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final String value, final long saveTime) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		if (value == null) {
			throw new RuntimeException("value不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(getRedisSerializer().serialize(key), saveTime,
						getRedisSerializer().serialize(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 保存一个对象一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final Object value, final long saveTime) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(getRedisSerializer().serialize(key), saveTime,
						RedisUtil.getBytesFromObject(value));
				return true;
			}
		});
		return result;
	}

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean set(final List<SaveRedisBean> list) {
		List<Object> result = redisTemplate
				.execute(new SessionCallback<List<Object>>() {
					public List<Object> execute(RedisOperations operations)
							throws DataAccessException {
						operations.multi();
						for (SaveRedisBean bean : list) {
							if (bean.getKey() == null) {
								throw new RuntimeException("key不可以是null");
							}
							BoundValueOperations<String, Object> oper = operations
									.boundValueOps(bean.getKey());
							oper.set(RedisUtil.getJsonFromObject(bean
									.getValue()));

						}
						return operations.exec();
					}
				});
		return true;
	}

	/**
	 * 从redis中查询对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(final String key, final Class<T> clazz) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		return redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] serialKey = getRedisSerializer().serialize(key);
				if (connection.exists(serialKey)) {
					byte[] value = connection.get(serialKey);
					return RedisUtil.getObjectFromBytes(value, clazz);
				}
				return null;
			}
		});
	}

	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] serialKey = getRedisSerializer().serialize(key);
				if (connection.exists(serialKey)) {
					byte[] value = connection.get(serialKey);
					return getRedisSerializer().deserialize(value);
				}
				return null;
			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delete(String key) {
		if (key == null) {
			throw new RuntimeException("key不可以是null");
		}
		redisTemplate.delete(key);
	}

	/**
	 * 批量删除
	 * 
	 * @param keys
	 */
	public void delete(List keys) {
		if (keys == null || keys.isEmpty() == true) {
			return;
		}
		redisTemplate.delete(keys);
	}
	/**
	 * 入队列
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, String value){
		redisTemplate.opsForList().rightPush(key, value);  
	}
	/**
	 * 入队列
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, Object value){
		redisTemplate.opsForList().rightPush(key, RedisUtil.getJsonFromObject(value));  
	}
	/**
	 * 出队列为一个字符串
	 * @param key
	 * @return
	 */
	public String outQueue(final String key){
		return redisTemplate.opsForList().leftPop(key);
	}
	/**
	 * 正则删除
	 * 
	 * @param key
	 */
	public void deletePattern(String patternKey){
		if (patternKey == null) {
			throw new RuntimeException("key不可以是null");
		}
		if(patternKey.indexOf("*")<0){
			throw new RuntimeException("没有通配符*");
		}
		Set<String> keys=redisTemplate.keys(patternKey);
		if(!keys.isEmpty()){
			redisTemplate.delete(keys);
		}
	}
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		context.start();
		System.out.println("加载完毕");
		RedisServiceImpl redisServer = context.getBean(RedisServiceImpl.class);
//		// //测试批量删除
//		// List delKeys=new ArrayList();
//		// delKeys.add("test");
//		// delKeys.add("test1");
//		// redisServer.delete(delKeys);
//		// 测试保存时间
//		redisServer.set("time", "hava", 10);
//		// 测试批量添加
//		List<SaveRedisBean> rows = new ArrayList<SaveRedisBean>();
//		TestBean user = new TestBean();
//		SaveRedisBean bean = new SaveRedisBean();
//		bean.setKey("0");
//		bean.setValue(user);
//		user.setName("密码111");
//		rows.add(bean);
//		// 测试事务
//		// bean=new SaveRedisBean();
//		// bean.setKey(null);
//		// rows.add(bean);
//		user = new TestBean();ch
//		bean = new SaveRedisBean();
//		bean.setKey("6");
//		bean.setValue(user);
//		user.setName("密码66");
//		rows.add(bean);
//		user = new TestBean();
//		bean = new SaveRedisBean();
//		bean.setKey("7");
//		bean.setValue(user);
//		user.setName("密码33");
//		rows.add(bean);
//		redisServer.set(rows);
//		// 测试单个删除
//		// redisServer.delete("test");
//		user = new TestBean();
//		user.setName("密码111");
//		System.out.println(redisServer.set("test", user));
//		;
//		System.out.println(redisServer.set("", new Object()));
//		;
//		// System.out.println(redisServer.get(null));;
//		TestBean userSearch = redisServer.get("test", TestBean.class);
//		System.out.println(userSearch.getName());
//		System.out.println(redisServer.get("test"));
		redisServer.deletePattern("*cache*");
	}
}
