<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>文档标题</title>
<script type="text/javascript" src="./script/sockjs.min.js"></script>
<script type="text/javascript" src="./script/reconnecting-websocket.js"></script>
</head>
 
<body>
	websocket test
	<input type="text" id ="msg"/> <button onclick="send()">发送</button>
</body>
 <script>
 var host = window.location.host+"/web_dep";
 var websocket;
 if ('WebSocket' in window) {
      websocket = new ReconnectingWebSocket("ws://"
         + host + "/test", null, {debug:true, maxReconnectAttempts:1}); 
      websocket.readyState
    /*  websocket = new WebSocket("ws://"
             + host + "/test"); */
 } else if ('MozWebSocket' in window) {
     websocket = new MozWebSocket("ws://" + host
         + "/test");
 } else {
     websocket = new SockJS("http://" + host
             + "/sockjs/test");
 }
 websocket.onopen = function(evnt) {
     console.log("websocket connected"+websocket.readyState);
 };
 websocket.onmessage = function(evnt) {
    //messageHandler(evnt.data);
    console.log(websocket.readyState+" "+evnt);
 };
 websocket.onerror = function(evnt) {
     console.log("websocket error");
 };
 websocket.onclose = function(evnt) {
     console.log("websocket close"+websocket.readyState);
 }

 
 function send(){
	 if(null!=websocket){
		 websocket.send(document.getElementById("msg").value);
	 }
 }
 </script>
</html>