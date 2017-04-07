package com.huaming.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaming.redis.dao.impl.RedisClientTemplate;

public class TestListener {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:resources/spring/spring-applicationContext-redis-c.xml");;
       /* while (true) { //这里是一个死循环,目的就是让进程不退出,用于接收发布的消息
            try {
                System.out.println("current time: " + new Date());

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
		RedisClientTemplate client = (RedisClientTemplate) ctx.getBean("redisClientTemplate");
		client.set("wzm.test", "wzm");
		System.out.println(client.get("wzm.test"));
		
	}
}
