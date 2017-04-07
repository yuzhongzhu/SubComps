package com.huaming.redis.dao;

import redis.clients.jedis.Jedis;

public interface IRedisDataSource {
	/**
	 * 获取redis 操作客户端
	 * @return
	 */
	public abstract Jedis getRedisClient();
    /**
     * 返还资源
     * @param shardedJedis
     * @param broken  异常返还时传 true 正常处理返还时传 false
     */
    public void returnResource(Jedis jedis,boolean broken);
}
