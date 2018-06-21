$(document).ready(function () {
    var webSocket = connect();

    $('#send').keyup(function (t) {

        if (t.ctrlKey && t.which == 13) {
            console.log(webSocket);
            if (isNull(sendContent)) {
                alert("不要发空字符串，好吗？");
                return;
            }
            //发送消息的接收人
            var receiver = $('li[class="active"] p').html();
            //发送消息的内容
            var sendContent = $("#send").val();
            //发送消息时间
            var timestamp = getCurrentTimestamp();

            var message = {
                "avatar": $.cookie('nickName'),
                "type": "user",
                "content": sendContent,
                "time": timestamp,
                "receiver": receiver
            };
            console.log(message);
            //消息记录插入到聊天框
            var msg = '<li _v-b412eea0="">' +
                '<p class="time" _v-b412eea0=""> <span _v-b412eea0="">' + timestampToTime(timestamp) + '</span> </p>' +
                '<div class="main self" _v-b412eea0=""> ' +
                '<img class="avatar" width="30" height="30" _v-b412eea0="" src="image/head/' + message.avatar + '.png">' +
                '<div class="text" _v-b412eea0="">' + message.content + '</div>' +
                '</div>' +
                '</li>';
            $('#msglist').append(msg);
            scrollToBottom('msgDiv');
            //清除输入框的内容
            $("#send").val("");
            //使用websocket发送消息
            var jsonstr = JSON.stringify(message);
            console.log("发送的消息:"+jsonstr);
            webSocket.send(jsonstr);
        }
    });
});


function loadOnlineFriends() {

    var nickName = $.cookie('nickName');
    $.ajax({
        url: '/friends/online',
        type: 'get',
        dataType: 'json',
        data: {'nickName': nickName},
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            $('#online').html("");
            $.each(data.data, function (index, value) {
                if (value.nickName != nickName) {
                    var clickFun = "selectFriend('" + value.nickName + "')";
                    if (index == 0) {
                        var friend = '<li _v-7e56f776="" class="active" id="f-' + value.nickName + '"  onclick=' + clickFun + '>' +
                            ' <img class="avatar" width="30" height="30" _v-7e56f776="" alt="在线好友" src="' + value.headPic + '">' +
                            '<p class="name" _v-7e56f776="">' + value.nickName + '</p>' +
                            '</li>';
                        $('#online').append(friend);
                    } else {
                        var friend = '<li _v-7e56f776="" id="f-' + value.nickName + '" onclick=' + clickFun + '>' +
                            ' <img class="avatar" width="30" height="30" _v-7e56f776="" alt="在线好友" src="' + value.headPic + '">' +
                            '<p class="name" _v-7e56f776="">' + value.nickName + '</p>' +
                            '</li>';
                        $('#online').append(friend);
                    }


                }
            });
        }
    });
}


function selectFriend(nickName) {
    //1.class="active"给选中的节点，其他节点去掉
    $('li[_v-7e56f776]').removeAttr("class");
    //2.给当前节点加上class="active"
    $('#f-' + nickName).attr('class', 'active');
    //2.1 清除未读消息标识
    $('#f-' + nickName + ' .weidu').remove();
    //3.切换聊天框 先清楚聊天框li，然后加载与该好友的聊天记录
    $('#msglist').children('li').remove();
    $.ajax({
        url: '/record/chat',
        type: 'get',
        data: {'friend': nickName},
        beforeSend: function (request) {
            request.setRequestHeader("authToken", $.cookie('authToken'));
        },
        success: function (data) {
            $.each(data.data, function (index, value) {
                var time = timestampToTime(value.time);
                if (value.avatar == $.cookie('nickName')) {
                    var msg = '<li _v-b412eea0="">' +
                        '<p class="time" _v-b412eea0=""> <span _v-b412eea0="">' + time + '</span> </p>' +
                        '<div class="main self" _v-b412eea0=""> ' +
                        '<img class="avatar" width="30" height="30" _v-b412eea0="" src="image/head/' + value.avatar + '.png">' +
                        '<div class="text" _v-b412eea0="">' + value.content + '</div>' +
                        '</div>' +
                        '</li>';
                    $('#msglist').append(msg);
                } else {
                    var msg = '<li _v-b412eea0="">' +
                        '<p class="time" _v-b412eea0=""> <span _v-b412eea0="">' + time + '</span> </p>' +
                        '<div class="main" _v-b412eea0=""> ' +
                        '<img class="avatar" width="30" height="30" _v-b412eea0="" src="image/head/' + value.avatar + '.png">' +
                        '<div class="text" _v-b412eea0="">' + value.content + '</div>' +
                        '</div>' +
                        '</li>'
                    $('#msglist').append(msg);
                }

            });
            scrollToBottom('msgDiv');
        }
    });

}

function loadMyInfo() {
    var headPic = $.cookie('headPic');
    var nickName = $.cookie('nickName');

    $('#myHead').attr('src', headPic);
    $('#myNickName').html(nickName);
}


function connect() {
    var authToken = $.cookie('authToken');
    var webSocket = new WebSocket('ws://localhost:8080/websocket/' + authToken);
    webSocket.onerror = function (event) {
        alert(event.data);
    };
    //与WebSocket建立连接
    webSocket.onopen = function (event) {
        console.log("与服务器建立连接！");
        console.log(webSocket);
        loadMyInfo();
        loadOnlineFriends();
    };
    //处理服务器返回的信息
    webSocket.onmessage = function (event) {
        var message = JSON.parse(event.data);
        console.log(message);
        handleMessage(message);
    };
    return webSocket;
}

function handleMessage(message) {
    var type = message.type;
    switch (type){
        case "user" :
            receiveMessage(message);
            break;
        case "online":
            onlineNotie(message);
            break;
        case "downline":
            downlineNotie(message);
            break;
    }
}

function onlineNotie(message) {
    loadOnlineFriends();
}

function downlineNotie() {
    loadOnlineFriends();
}

function receiveMessage(message) {
    //如果消息发送者的聊天框是选中状态，直接在聊天框追加，不是则在好友栏显示未读消息“1”
    //获取目前选中的好友
    var friend = $('li[class="active"] p').html();
    //如果消息发送者的聊天框是选中状态，直接在聊天框追加
    if (friend == message.avatar) {
        //消息记录插入到聊天框
        var msg = '<li _v-b412eea0="">' +
            '<p class="time" _v-b412eea0=""> <span _v-b412eea0="">' + message.time + '</span> </p>' +
            '<div class="main" _v-b412eea0=""> ' +
            '<img class="avatar" width="30" height="30" _v-b412eea0="" src="image/head/' + message.avatar + '.png">' +
            '<div class="text" _v-b412eea0="">' + message.content + '</div>' +
            '</div>' +
            '</li>';
        $('#msglist').append(msg);
        scrollToBottom('msgDiv');

    }else {
        //给好友栏的好友追加一个未读消息的标志
        var wdFriend = $('#f-'+message.avatar);
        var weidu = wdFriend.children('.weidu');
        var weiduCount = weidu.html();
        if (weiduCount == undefined){
            var weiduDiv = '<div class="weidu">'+1+'</div>';
            wdFriend.append(weiduDiv);
        }else {
            var weiduNumber = Number(weiduCount);
            weiduNumber += 1;
            weidu.html(weiduNumber);
        }
    }

}

function disconnect() {
    webSocket.close();
    document.getElementById('response').innerHTML += '<br />' + '与服务器端断开连接';
}

