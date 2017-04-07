package com.huaming.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.huaming.redis.dao.IRedisDataSource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Repository("redisDataSource")
@SuppressWarnings("restriction")
public class RedisDataSourceImpl implements IRedisDataSource{
	@Resource
    private JedisPool    jedisPool;
	public Jedis getRedisClient() {
		Jedis jedis = jedisPool.getResource();
	    return jedis;
	}

	@SuppressWarnings("deprecation")
	public void returnResource(Jedis shardedJedis, boolean broken) {
		if (broken) {
			jedisPool.returnBrokenResource(shardedJedis);
        } else {
        	jedisPool.returnResource(shardedJedis);
        }
		
	}

}
