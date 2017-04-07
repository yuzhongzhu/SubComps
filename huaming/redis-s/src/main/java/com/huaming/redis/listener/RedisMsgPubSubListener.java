package com.huaming.redis.listener;

import java.io.Serializable;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisPubSub;
@SuppressWarnings("restriction")
@Service
public class RedisMsgPubSubListener extends JedisPubSub {
	private static Logger logger = Logger.getLogger(RedisMsgPubSubListener.class); 
	@Resource(name = "redisTemplate")
	public  RedisTemplate<Serializable, Serializable> redisTemplate ;
	@Override
	public int getSubscribedChannels() {
		// TODO Auto-generated method stub
		return super.getSubscribedChannels();
	}

	@Override
	public boolean isSubscribed() {
		return super.isSubscribed();
	}

	@Override
	public void onMessage(String channel, String message) {
		logger.info("当前消息来源的通道是"+channel+"消息"+message);
		System.out.println("当前消息来源的通道是"+channel+"消息"+message);
		super.onMessage(channel, message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		super.onPMessage(pattern, channel, message);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		super.onPSubscribe(pattern, subscribedChannels);
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		super.onPUnsubscribe(pattern, subscribedChannels);
	}

	@Override
	public void onPong(String pattern) {
		super.onPong(pattern);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		logger.info("channel:" + channel + "is been subscribed:" + subscribedChannels); 
		super.onSubscribe(channel, subscribedChannels);
		
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		logger.info("channel:" + channel + "is been unsubscribed:" + subscribedChannels); 
		super.onUnsubscribe(channel, subscribedChannels);
	}

	@Override
	public void proceed(Client client, String... channels) {
		super.proceed(client, channels);
	}

	@Override
	public void proceedWithPatterns(Client client, String... patterns) {
		super.proceedWithPatterns(client, patterns);
	}

	@Override
	public void psubscribe(String... patterns) {
		super.psubscribe(patterns);
	}

	@Override
	public void punsubscribe() {
		super.punsubscribe();
	}

	@Override
	public void punsubscribe(String... patterns) {
		super.punsubscribe(patterns);
	}

	@Override
	public void subscribe(String... channels) {
		logger.info("创建订阅的通道"+channels);
		super.subscribe(channels);
	}

	@Override
	public void unsubscribe() {
		super.unsubscribe();
	}

	@Override
	public void unsubscribe(String... channels) {
		logger.info("解除订阅的"+channels);
		super.unsubscribe(channels);
	}
	
	public int send(String channel,String message){
		try {
			redisTemplate.convertAndSend(channel, message);
		} catch (Exception e) {
			logger.error("发送消息报错",e);
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	
	
}
