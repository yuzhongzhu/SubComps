package com.huaming.websocket.annotation;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
/**
 * 实现功能
 * 1、通信客户端的连接建立
 * 2、监听通信客户端发送上来的信息
 * 3、推送消息到客户端或者指定客户
 * @author Michael
 *
 */
@ServerEndpoint(value = "/test", configurator = BindHttpSessionConfiguration.class) 
public class WebSocketEndPoint {
	Logger logger = Logger.getLogger(WebSocketEndPoint.class);
	private static final String GUEST_PREFIX = "GUEST"; 
	private static final AtomicInteger connectionIds = new AtomicInteger(0);  
    private static final Set<WebSocketEndPoint> connections = new CopyOnWriteArraySet<WebSocketEndPoint>();  
  
    private final String nickname;  
    private Session session;  
    private static HttpSession httpSession;  
    public WebSocketEndPoint() {  
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();  
    }  
    
    @OnMessage
    public void onMessage(String message, Session session) 
      throws IOException, InterruptedException {
    
      // Print the client message for testing purposes
      System.out.println("Received: " + message);
      logger.info("Received: " + message);
      // Send the first message to the client
    
      // Send 3 messages to the client every 5 seconds
      int sentMessages = 0;
      while(sentMessages < 3){
        Thread.sleep(5000);
        session.getBasicRemote().
          sendText("This is an intermediate server message. Count: " 
            + sentMessages);
        sentMessages++;
      }
    
      // Send a final message to the client
      session.getBasicRemote().sendText("This is the last server message");
    }
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
      logger.info("ws  Client connected");
      System.out.println("Client connected"+session);
      this.session = session;  
      httpSession = (HttpSession) config.getUserProperties().get(  
              HttpSession.class.getName());  
      connections.add(this);  
      System.out.println(httpSession.getAttribute("name"));  
      String message = String.format("* %s %s", nickname, "has joined.");  
      broadcast(message);  
    }

    @OnClose
    public void onClose() {
      logger.info("ws  Connection closed");
      System.out.println("Connection closed");
      connections.remove(this);
      String message = String.format("* %s %s",
              nickname, " from websocket 已经离开...");
      System.out.println(message);
    }
    
    @OnError
    public void onError(Throwable t) throws Throwable{
    	 logger.error("错误: " + t.toString(), t);
    }
    
    private static void broadcast(String msg) {  
        for (WebSocketEndPoint client : connections) {  
            try {  
                synchronized (client) {  
                    client.session.getBasicRemote().sendText(msg);  
                }  
            } catch (IOException e) {  
                System.out.println("Chat Error: Failed to send message to client");  
                connections.remove(client);  
                try {  
                    client.session.close();  
                } catch (IOException e1) {  
                }  
                String message = String.format("* %s %s", client.nickname,  
                        "has been disconnected.");  
                broadcast(message);  
            }  
        }  
    } 
    
    /**
     * 给指定用户推送其订阅消息
     * @param userId
     * @param msg
     * @return
     */
    public static Map<String,Object> sendPublishMsgToUser(String userId,String msg){
    	return null;
    }
    /**
     * 群发推送消息
     * @param msg
     * @return
     */
    public static Map<String,Object> sendPublishMsg(String msg){
    	return null;
    }
    
}
