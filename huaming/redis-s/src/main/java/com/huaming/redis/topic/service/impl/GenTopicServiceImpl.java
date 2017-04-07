package com.huaming.redis.topic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huaming.redis.Exception.RedisServiceException;
import com.huaming.redis.dao.impl.RedisClientTemplate;
import com.huaming.redis.topic.service.IGenTopicService;
/*@Service("genTopicService")*/
public class GenTopicServiceImpl implements IGenTopicService {
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	public boolean createNewTopic(String key, String topics) throws RedisServiceException {
		if("[]".equals(topics)){
			topics="";
		}
		redisClientTemplate.set(key, topics);
		return true;
	}

	public List<String> getAllTopics(String key) throws RedisServiceException {
		String topicsStr = redisClientTemplate.get(key);
		if(null==topicsStr||"".equals(topicsStr)||"[]".equals(topicsStr)){
			return null;
		}
		return ConvertJson2List(topicsStr);
	}

	public boolean updateTopicsInfo(String key,String values) throws RedisServiceException {
		if("[]".equals(values)){
			values="";
		}
		redisClientTemplate.set(key, values);
		return true;
	}
	
	
	private static List<String> ConvertJson2List(String value){
		return JSONObject.parseArray(value, String.class);
	}
}
