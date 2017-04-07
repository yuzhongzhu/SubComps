package com.huaming.redis.util;

import com.huaming.redis.listener.RedisMsgPubSubListener;

import redis.clients.jedis.JedisPubSub;

public class RedisUtil {
	
	/**
	 * 当前接收的消息
	 * @param channel
	 * @param message
	 * @param listener
	 */
	public static void onMessage(String channel,String message,JedisPubSub listener){
		listener.onMessage(channel, message);
	}
	
	/**
	 * 当前被订阅的通道
	 * @param channel
	 * @param subscribedChannels
	 * @param listener
	 */
	public static void onSubscribe(String channel,int subscribedChannels ,JedisPubSub listener){
		listener.onSubscribe(channel, subscribedChannels);
	}
	/**
	 * 创建订阅通道
	 * @param listener
	 * @param channels
	 */
	public static void subscribe(JedisPubSub listener,String ...channels){
		listener.subscribe(channels);
	}

	/**
	 * 发布消息
	 * @param channel
	 * @param subscribedChannels
	 * @param listener
	 */
	public static void publishMsg(String channel,String message ,RedisMsgPubSubListener listener){
		listener.send(channel, message);
	}
	
}
