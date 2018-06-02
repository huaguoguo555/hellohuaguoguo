
var app=angular.module("chatRoom",['ngWebSocket']);

app.factory('MyData', function ($websocket) {
        // Open a WebSocket connection
        var token  = guid();
        var dataStream = $websocket('ws://localhost:8080/websocket/'+token);

        var messages = [];

        messages.push('欢迎来到建华联盟');
        dataStream.onMessage(function (message) {
            messages.push(JSON.parse(message.data).data.desp);
        });

        var methods = {
            messages: messages,
            get: function () {
                dataStream.send(JSON.stringify({action: 'get'}));
            }
        };

        return methods;
    });

app.controller('chatCtrl', function ($scope,MyData) {
        alert(MyData);
        $scope.MyData = MyData;
    });


function guid() {
    function S4() {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    }
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}