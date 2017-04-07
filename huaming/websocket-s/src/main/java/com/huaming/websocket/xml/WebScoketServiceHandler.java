package com.huaming.websocket.xml;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.huaming.websocket.util.WebSocketConstant;
@Component("webSocketHandler")
public class WebScoketServiceHandler extends TextWebSocketHandler {
	private static final Logger logger = Logger.getLogger(WebScoketServiceHandler.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		 logger.info("websocket chat connection closed......");
	     users.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		System.out.println("connect to the websocket success......");
		logger.info("connect to the websocket success......");
		users.add(session);
		String userName = (String) session.getAttributes().get(WebSocketConstant.WEBSOCKET_USERNAME);
		if (userName != null) {
			// 查询未读消息
			session.getAttributes().get(WebSocketConstant.WEBSOCKET_USERNAME);
			// int count = webSocketService.getUnReadNews((String));

			session.sendMessage(new TextMessage("teststa"));
		}
	}
	/**
	 * 接收客户端发送消息并处理
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		logger.info("接收到的客户端信息"+message.getPayload());
		session.sendMessage(new TextMessage(message.getPayload()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		super.handleMessage(session, message);
	}


	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		if(session.isOpen()){
            session.close();
        }
        logger.info("websocket chat connection closed......");
        users.remove(session);
	}
	
}
