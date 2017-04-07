package com.huaming.redis.lock.util;

import java.util.concurrent.TimeUnit;

import org.redisson.core.RLock;

public class RedisLockUtil {
	/**
	 * 锁定
	 * @param lock
	 */
	public static void lock(RLock lock){
		lock.lock();
	}
	/**
	 * 
	 * @param lock
	 * @param releaseTime 设置多长时间自动释放锁
	 */
	public static void lock(RLock lock,long releaseTime) throws Exception{
		lock.lock(releaseTime, TimeUnit.MILLISECONDS);
	}
	/**
	 * 可重入锁，即当前线程再次进入后即放行，但是进入几次也需要相应的释放几次，才可完全释放
	 * @param lock
	 */
	public static void tryLock(RLock lock)throws Exception{
		lock.tryLock();
	}
	
	public static void tryLock(RLock lock,long waitTime) throws InterruptedException{
		lock.tryLock(waitTime,TimeUnit.MILLISECONDS);
	}
	public static void tryLock(RLock lock,long waitTime,long releaseTime) throws InterruptedException{
		lock.tryLock(waitTime,releaseTime,TimeUnit.MILLISECONDS);
	}
	
	public static void unLock(RLock lock)throws Exception{
		lock.unlock();
	}
	 public static void forceUnLock(RLock lock)throws Exception{
		 lock.forceUnlock();
	 }
}
