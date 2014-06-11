package org.family.spring.data.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.family.spring.data.redis.base.RedisServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试redis使用字符串存储 编码表的性能
 * 本地测试排除了 网络 10w数据性能很好 但字段变长 速度明显变慢 
 * 数据量不大的时候 字段稍长影响不大
 * @author wuzl
 * 
 */
public class TestRedisStrForSysparamCapability {
	private static int count = 1000000;

	public static void insert(RedisServiceImpl redisServer) {
		// 模拟插入编码表数据
		List<Map> rows = new ArrayList<Map>();
		int param=0;
		int j = 0;
		int random = 4;
		for (int i = 0; i < count; i++) {
			j++;
			Map dto = new HashMap();
			dto.put("param", "param" + param);
			dto.put("value", j);
			dto.put("desc", "测试的desc" + j);
			dto.put("show", "测试的show" + j);
			dto.put("parent", j);
			if (j % random == 0) {
				j = 0;
				param++;
			}
			rows.add(dto);
		}
		// 插入redis中
		Date begin = new Date();
		redisServer.set("param_str", rows);
		Date end = new Date();
		// 1w条206 10w330 100w2415
		System.out.println("插入时间" + (end.getTime() - begin.getTime()));
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		context.start();
		RedisServiceImpl redisServer = context.getBean(RedisServiceImpl.class);
		System.out.println("加载完毕");
//		insert(redisServer);
//		System.out.println(redisServer.get("param_str"));
		//开始查询某个
		Date begin = new Date();
		List<Map> result=new ArrayList<Map>();
		List<Map> rows=redisServer.get("param_str",List.class);
		for(Map dto:rows){
			if(dto.get("param").equals("param25000")){
				result.add(dto);
			}
		}
		Date end = new Date();
		// 1w条118-219 10w415 100w1236 加如两字断扩3064
		System.out.println("查询时间" + (end.getTime() - begin.getTime()));
		System.out.println(result);
	}
}
