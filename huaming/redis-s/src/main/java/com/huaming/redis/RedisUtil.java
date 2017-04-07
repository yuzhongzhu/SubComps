package com.huaming.redis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class RedisUtil {
	public static Logger logger = Logger.getLogger(RedisUtil.class);
	public static Properties redisProp;

	public static void loadProp() {
		InputStream ins = null;
		try {
			ins = new FileInputStream("classpath:/resources/prop/redis_conf.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		redisProp = new Properties();
		try {
			redisProp.load(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getPropVlByKey(String keyName) {
		return redisProp.get(keyName);
	}

	public static void main(String[] args) {
		loadProp();
		String host = String.valueOf(getPropVlByKey("redis.host"));
		String password= String.valueOf(getPropVlByKey("redis.pwd"));
		Jedis jedis = new Jedis(host);
		jedis.auth(password);
		System.out.println(jedis.ping());
	}
	
	
}
