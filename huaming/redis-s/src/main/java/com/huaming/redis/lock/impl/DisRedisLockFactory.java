package com.huaming.redis.lock.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RLock;

import com.huaming.redis.lock.DisRedisLock;
/**
 * 服务器启动初始化分布式
 * 缓存配置信息
 * @author Michael
 *
 */
public class DisRedisLockFactory implements DisRedisLock {
	private static final Logger logger = Logger.getLogger(DisRedisLockFactory.class);
	
	private Redisson redisson;

	public RLock setLock(String key) {
		RLock lock = redisson.getLock(key);
		return lock;
	}

	public void doInit() {
		if (null == redisson) {
			logger.debug("初始化分布式锁模板");
			InputStream inputStream = null;
			Config config = null;
			try {
				inputStream = DisRedisLockFactory.class.getClassLoader().getResourceAsStream("redisson-conf.json");
				config = Config.fromJSON(inputStream);
				this.redisson = ((Redisson) Redisson.create(config));
			} catch (IOException e) {
				logger.error("读取Redisson配置失败", e);
			} finally {
				if (inputStream != null)
					try {
						inputStream.close();
					} catch (IOException e) {
						logger.error("", e);
					}
			}
		}
		
	}
	
}
