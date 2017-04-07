package com.huaming.redis.topic.service;

import java.util.List;

import com.huaming.redis.Exception.RedisServiceException;

public interface IGenTopicService {
	/**
	 * 获取当前服务的所有通道
	 * @param key
	 * @return
	 * @throws RedisServiceException
	 */
	public List<String> getAllTopics(String key)throws RedisServiceException;
	/**
	 * 更新当前服务存在通道信息(新增或者删除订阅通道)
	 * @param key
	 * @param values
	 * @return
	 * @throws RedisServiceException
	 */
	public boolean updateTopicsInfo(String key,String values)throws RedisServiceException;
}
