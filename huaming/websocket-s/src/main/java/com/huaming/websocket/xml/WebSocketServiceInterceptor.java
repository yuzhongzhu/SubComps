package com.huaming.websocket.xml;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class WebSocketServiceInterceptor extends HttpSessionHandshakeInterceptor {
	private static Logger logger = Logger.getLogger(HandshakeInterceptor.class);

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		logger.info("beforeHandshake==============");
        return  super.beforeHandshake(request, response, handler, attributes); 
	}
	
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception exception) {
		super.afterHandshake(request, response, handler, exception);
		
	}
}
