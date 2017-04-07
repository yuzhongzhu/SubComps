package com.huaming.websocket.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.huaming.websocket.xml.WebScoketServiceHandler;
import com.huaming.websocket.xml.WebSocketServiceInterceptor;

/**
 * 注解形式实现websocket
 * 1、实现WebSocketConfigurer接口，重写registerWebSocketHandlers方法，这是一个核心实现方法，配置websocket入口，允许访问的域、注册Handler、SockJs支持和拦截器。
 * 2、registry.addHandler注册和路由的功能，当客户端发起websocket连接，把/path交给对应的handler处理，而不实现具体的业务逻辑，可以理解为收集和任务分发中心。
 * 3、setAllowedOrigins(String[] domains),允许指定的域名或IP(含端口号)建立长连接，如果只允许自家域名访问，这里轻松设置。如果不限时使用"*"号，如果指定了域名，则必须要以http或https开头。
 * 4、addInterceptors，顾名思义就是为handler添加拦截器，可以在调用handler前后加入我们自己的逻辑代码。
 * @Configuration    指明该类为Spring 配置类
 * @EnableWebSocket  声明该类支持WebSocket
 * @author Michael
 *
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebScoketConfig extends  WebMvcConfigurerAdapter implements WebSocketConfigurer {

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		/**
		 * 设置允许连接的域
		 */
		//String[] allowsOrigins = {"http://www.xxx.com"};
		/*registry.addHandler(getWebScokerHandler(), "/test").setAllowedOrigins("*").addInterceptors(getInterceptor());
		registry.addHandler(getWebScokerHandler(), "/sockjs/test").setAllowedOrigins("*").addInterceptors(getInterceptor()).withSockJS();*/
		registry.addHandler(getWebScokerHandler(), "/test").setAllowedOrigins("*");
		registry.addHandler(getWebScokerHandler(), "/sockjs/test").setAllowedOrigins("*").withSockJS();
	}
	@Bean
	public WebScoketServiceHandler getWebScokerHandler(){
		return new WebScoketServiceHandler();
	}
	
	public WebSocketServiceInterceptor getInterceptor(){
		return new WebSocketServiceInterceptor();
	}
	
	
	
}
