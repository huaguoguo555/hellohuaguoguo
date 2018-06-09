$(document).ready(function(){
    var webSocket = null;
    connect();
});


function send() {
    var sendContent = $("#sendContent").val();
    //向服务器发送请求
    webSocket.send(sendContent);
}

function connect() {
    var authToken = $.cookie('authToken');
    webSocket = new WebSocket('ws://localhost:8080/websocket/'+authToken);
    webSocket.onerror = function(event) {
        alert(event.data);
    };
    //与WebSocket建立连接
    webSocket.onopen = function(event) {
        console.log("与服务器建立连接！");
    };
    //处理服务器返回的信息
    webSocket.onmessage = function(event) {
        var message = event.data;


    };
}

function disconnect() {
    webSocket.close();
    document.getElementById('response').innerHTML += '<br />'+ '与服务器端断开连接';
}

