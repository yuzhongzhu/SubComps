package com.huaming.redis.lock;

import org.redisson.core.RLock;

public interface DisRedisLock {
	public RLock setLock(String key);
	public void doInit();
	
}
