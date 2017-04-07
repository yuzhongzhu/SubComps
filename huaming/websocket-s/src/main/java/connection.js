var host = window.location.host;
var websocket;
if ('WebSocket' in window) {
    websocket = new ReconnectingWebSocket("ws://"
        + host + "/topic", null, {debug:true, maxReconnectAttempts:4});
} else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://" + host
        + "/topic");
} else {
    websocket = new SockJS("http://" + host
            + "/sockjs/topic");
}
websocket.onopen = function(evnt) {
    console.log("websocket连接上");
};
websocket.onmessage = function(evnt) {
    messageHandler(evnt.data);
};
websocket.onerror = function(evnt) {
    console.log("websocket错误");
};
websocket.onclose = function(evnt) {
    console.log("websocket关闭");
}
