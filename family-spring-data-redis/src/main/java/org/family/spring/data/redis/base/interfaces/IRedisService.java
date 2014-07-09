package org.family.spring.data.redis.base.interfaces;


import java.util.List;

import org.family.spring.data.redis.support.SaveRedisBean;


public interface IRedisService {
	/**
	 * 保存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final Object value);

	/**
	 * 保存字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, final String value);

	/**
	 * 保存一个字符串一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final String value, final long saveTime);

	/**
	 * 保存一个对象一定时间
	 * 
	 * @param key
	 * @param value
	 * @param saveTime过期时间
	 *            单位为秒
	 * @return
	 */
	public boolean set(final String key, final Object value, final long saveTime);

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean set(final List<SaveRedisBean> list);

	/**
	 * 从redis中查询对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(final String key, final Class<T> clazz);

	/**
	 * 从redis中查询出字符串
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 批量删除
	 * 
	 * @param keys
	 */
	public void delete(List keys);
	/**
	 * 入队列
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, String value);
	/**
	 * 入队列
	 * @param key
	 * @param value
	 */
	public void inQuere(String key, Object value);
	/**
	 * 出队列为一个字符串
	 * @param key
	 * @return
	 */
	public String outQueue(final String key); 
	
	/**
	 * 正则删除
	 * 
	 * @param key
	 */
	public void deletePattern(String key);
}
