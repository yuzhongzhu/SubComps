package com.huaming.websocket.annotation;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

public class BindHttpSessionConfiguration extends Configurator {

	@Override
	public boolean checkOrigin(String originHeaderValue) {
		// TODO Auto-generated method stub
		return super.checkOrigin(originHeaderValue);
	}

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		 HttpSession httpSession=(HttpSession) request.getHttpSession();
		 if(null==httpSession){
			 return;
		 }
		 sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
		// super.modifyHandshake(sec, request, response);
	}
	
}
