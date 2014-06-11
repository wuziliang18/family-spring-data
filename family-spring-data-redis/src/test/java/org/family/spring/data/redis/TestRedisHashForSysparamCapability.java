package org.family.spring.data.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.family.spring.data.redis.base.interfaces.IRedisHashService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试hash来缓存syspam表性能
 * 排除了网络原因 使用本地的
 * 插入速度不如str的因为没有使用批量的 使用批量后速度似乎更慢
 * 获取速度1w左右与字符串差不多 但是网络明显会又影响
 * 数据量越大 速度越快 且与field的顺序有关
 * @author wuzl
 *
 */
public class TestRedisHashForSysparamCapability {
	private static int count = 100000;

	public static void insert(IRedisHashService redisHashServer ) {
		// 模拟插入编码表数据
		int param=0;
		int j = 0;
		int random = 4;
		List<Map> insertRows= new ArrayList<Map>();
		// 插入redis中
		Date begin = new Date();
		for (int i = 0; i < count; i++) {
			j++;
			Map dto = new HashMap();
			dto.put("param", "param" + param);
			dto.put("value", j);
			dto.put("desc", "测试的desc" + j);
			dto.put("show", "测试的show" + j);
			dto.put("parent", j);
			insertRows.add(dto);
			if (j % random == 0) {
				j = 0;
				param++;
				redisHashServer.hset("param_hash","param" + param, insertRows);
				insertRows= new ArrayList<Map>();
			}
		}
		Date end = new Date();
		// 1w条591 10w2035 100w2415
		System.out.println("插入时间" + (end.getTime() - begin.getTime()));
	}
	public static void insertUserPipe(IRedisHashService redisHashServer ) {
		// 模拟插入编码表数据
		int param=0;
		int j = 0;
		int random = 4;
		Map<String,Object> valueMap=new HashMap();
		List<Map> insertRows= new ArrayList<Map>();
		Date begin = new Date();
		int insertCount=0;
		// 插入redis中
		for (int i = 0; i < count; i++) {
			j++;
			Map dto = new HashMap();
			dto.put("param", "param" + param);
			dto.put("value", j);
			dto.put("desc", "测试的desc" + j);
			dto.put("show", "测试的show" + j);
			dto.put("parent", j);
			insertRows.add(dto);
			if (j % random == 0) {
				valueMap.put("param" + param, insertRows);
				j = 0;
				param++;
				insertCount++;
				insertRows= new ArrayList<Map>();
			}
			if(insertCount>200){
				redisHashServer.hset("param_hash", valueMap);
				valueMap=new HashMap();
				insertCount=0;
			}
		}
		Date end = new Date();
		// 1w条2585 10w 100w
		System.out.println("插入时间" + (end.getTime() - begin.getTime()));
	}
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		context.start();
		IRedisHashService redisHashServer = context.getBean(IRedisHashService.class);
		RedisServiceImpl redisServer = context.getBean(RedisServiceImpl.class);
		//先删除
		redisServer.delete("param_hash");
		System.out.println("加载完毕");
		insert(redisHashServer);
		//开始查询某个
		Date begin = new Date();
		List<Map> result=redisHashServer.hget("param_hash", "param250000", List.class);
		Date end = new Date();
//		// 1w条125 10w124 100w1236 加如两字断扩3064
		System.out.println("查询时间" + (end.getTime() - begin.getTime()));
		System.out.println(result);
	}
}
