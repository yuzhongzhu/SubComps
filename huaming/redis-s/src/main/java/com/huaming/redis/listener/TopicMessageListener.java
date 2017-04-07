package com.huaming.redis.listener;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
@SuppressWarnings("restriction")
public class TopicMessageListener implements MessageListener {
	
	@Resource(name = "redisTemplate")
	public  RedisTemplate<Serializable, Serializable> redisTemplate ;
	public void onMessage(Message paramMessage, byte[] paramArrayOfByte) {
		byte[] body = paramMessage.getBody();// 请使用valueSerializer  
        byte[] channel = paramMessage.getChannel();  
        // 请参考配置文件，本例中key，value的序列化方式均为string。  
        // 其中key必须为stringSerializer。和redisTemplate.convertAndSend对应  
        String msgContent = (String) redisTemplate.getValueSerializer().deserialize(body);  
        String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);  
        System.out.println(topic + ":" + msgContent);  
	}
}
