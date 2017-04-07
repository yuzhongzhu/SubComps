package com.huaming.redis.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.alibaba.fastjson.JSONObject;
import com.huaming.redis.dao.impl.RedisClientTemplate;

public class GenTopicListener {
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private RedisMessageListenerContainer listenerContainer;
	
	
	public void doInit(){
		//已json 形式进行存储
		String channelTopics = redisClientTemplate.get("topics");
		List<String> topicList = JSONObject.parseArray(channelTopics, String.class);
		for(String topicName:topicList){
			//TODO
		}
		
	}


	public RedisMessageListenerContainer getListenerContainer() {
		return listenerContainer;
	}


	public void setListenerContainer(RedisMessageListenerContainer listenerContainer) {
		this.listenerContainer = listenerContainer;
	}
	
	
	
}
