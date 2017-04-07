package com.huaming.redis.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huaming.redis.util.RedisUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:resources/spring/spring-applicationContext-redis.xml"})
public class RedisMsgPubSubListenerTest {
	@Autowired
	RedisMsgPubSubListener redisMsgPubSubListener ;
	@Test
	public void testSend() {
		RedisUtil.publishMsg("wzm.test1.ch", "baobao", redisMsgPubSubListener);
	}
	
	
}
