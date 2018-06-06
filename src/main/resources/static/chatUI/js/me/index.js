var _imgUrl = 'image/head/';
var localPicUrl = 'image/pic/'
var _picUrl = 'http://c1.mifile.cn/f/i/hd/2016051101/';
var me = {
    id: 'me', // 写死的，不用改
    name: '游客', // 用户昵称
    avatar: localPicUrl, // 用户头像图片 url
    userId: '', // 用户id
    registered: false // 用户是否已经预约
};
var gif = {
    welcome: '<img src="' + _picUrl + 'welcome.gif' + '">',
    lol: '<img src="' + _picUrl + 'lol.gif' + '">',
    cry: '<img src="' + _picUrl + 'cry.gif' + '">',
    cz: '<img src="' + localPicUrl + 'chuizi.png' + '">',
};
var _animation = {
    tour: $('.J_map').html(),
};
var _userName = me.name;
var _dialog = {};
var _members = {};

function geneDialog(userName) {
    var defaultMembers = {
        rtk: {
            id: 'rtk',
            name: '荣头坤',
            avatar: _imgUrl + 'a-rtk.png',
        },
        zsj: {
            id: 'zsj',
            name: '朱圣杰',
            avatar: _imgUrl + 'a-zsj.png',
        },
        tr: {
            id: 'tr',
            name: '陶然',
            avatar: _imgUrl + 'a-tr.png',
        },
        zk: {
            id: 'zk',
            name: '赵凯',
            avatar: _imgUrl + 'a-zk.png',
        },
        xb: {
            id: 'xb',
            name: '小逼',
            avatar: _imgUrl + 'a-xb.png',
        }
    };
    _members = $.extend(_members, defaultMembers);


    // 引导对话
    _dialog.d0 = [{
        type: 'plain',
        author: _members.tr,
        content: userName + ' 你好！',
        pause: 2000,
    }, {
        type: 'plain',
        author: _members.xb,
        content: '欢迎 ' + userName + gif.welcome + gif.welcome + gif.welcome,
        flag: 'emoji-welcome',
    }, {
        type: 'plain',
        author: _members.rtk,
        content: '你们渣渣呜呜个' + gif.cz + '呢',
    }, {
        type: 'plain',
        author: _members.zsj,
        content: '嚯嚯嚯',
    }, {
        type: 'plain',
        author: _members.zk,
        content: '我买了个16000的mac，嘿嘿嘿',
    }, ];




    // 关于求红包的对话
    _dialog.d8 = [{
        type: 'plain',
        author: _members.me,
        content: '我听了这么多，老板们发个红包呗',
    }, {
        type: 'system',
        content: '生态链刘德退出群聊',
        pause: 500,
    }, {
        type: 'system',
        content: '小米网林斌退出群聊',
        pause: 400,
    }, {
        type: 'system',
        content: 'MIUI洪锋退出群聊',
        pause: 700,
    }, {
        type: 'system',
        content: '小米电视王川退出群聊',
        pause: 400,
    }, {
        type: 'system',
        content: '小米路由云KK退出群聊',
        pause: 500,
    }, {
        type: 'system',
        content: '手机研发周光平退出群聊',
        pause: 1500,
    }, {
        type: 'plain',
        author: _members.lwq,
        content: '哈哈，大家开个玩笑，微信公众号现在不允许H5发红包了哦..',
    }, ];
}

$.fn.scrollSmooth = function(scrollHeight, duration) {
    var $el = this;
    var el = $el[0];
    var startPosition = el.scrollTop;
    var delta = scrollHeight - startPosition;
    var startTime = Date.now();

    function scroll() {
        var fraction = Math.min(1, (Date.now() - startTime) / duration);
        el.scrollTop = delta * fraction + startPosition;
        if (fraction < 1) {
            setTimeout(scroll, 10);
        }
    }
    scroll();
};

$.fn.goSmooth = function(height, duration) {
    var $el = this;
    var startPosition = 1 * $el.css('margin-bottom').replace('px', '');
    var delta = height - startPosition;
    var startTime = Date.now();

    function scroll() {
        var fraction = Math.min(1, (Date.now() - startTime) / duration);
        $el.css('margin-bottom', delta * fraction + startPosition);
        if (fraction < 1) {
            setTimeout(scroll, 10);
        }
    }
    scroll();
};

var $chat = $('#chatContent');

function Queue() {};
Queue.prototype = {
    add: function(el) {
        if (this._last) {
            this._last = this._last._next = { //_last是不断变的
                el: el,
                _next: null //设置_last属性表示最后一个元素，并且让新增元素成为它的一个属性值
            }
        } else {
            this._last = this._first = { //我们要设置一个_first属性表示第一个元素
                el: el,
                _next: null
            }
        }
        return this;
    }
}

function copyQueue(p) {
    var queue = new Queue;
    for (var i = 0; i < p.length; i++) {
        queue.add(p[i]);
    }
    return queue;
};



function showChoice(choice, delay) {
    $('.J_noticeInput').hide();

    if (delay === null) {
        delay = 100;
    }
    if (!choice) {
        choice = '0';
    }

    setTimeout(function() {
        $('.J_choiceWrapper').addClass('opened').find('.J_choice').removeClass('choosen').hide();
        $('.J_inputWrapper').addClass('opened');
        var $choiceUl = $('.J_choiceWrapper').find('.J_choice').filter('[data-choice="' + choice + '"]');
        var cH = $choiceUl.addClass('choosen').show().height();
        var ftH = $('.box_ft').height();
        var sOH = $('#chatContent').height();

        $('.box_bd').goSmooth(cH, 100);
        $('.J_scrollContent').scrollSmooth(ftH + sOH, 300);

    }, delay);
}

function hideChoice() {
    $('.box_bd').goSmooth(0, 100);
    $('.J_inputWrapper').removeClass('opened');
    $('.J_choiceWrapper').removeClass('opened');
}

function playTour() {
    $('.J_mapWrapper').addClass('animate');
    var i = 0;
    var interval = setInterval(function() {
        if (i >= 9) {
            $('.J_tourtime').html(8);
            clearInterval(interval);
        } else {
            i++;
            $('.J_tourtime').html(i % 9);
        }
    }, 5000 / 9);

}

function showDialog(dialog, cb) {

    var message = copyQueue(dialog)._first;
    var tpl = doT.template($('#messageTpl').html());

    function loop(delay) {
        // speed
        if (delay == undefined) {
            // random delay between messages
            delay = Math.random() * 1000 + 600;
            //delay = Math.random() * 50 + 50;
        }

        var timeout = setTimeout(function() {
            if (message) {
                // 显示 message
                var messageHtml = tpl([message.el]);
                $chat.append(messageHtml);

                // 自动滚动
                var viewH = $('.J_scrollContent').height();
                var contentH = $chat.height();
                if (contentH > viewH) {
                    $('.J_scrollContent').scrollSmooth(contentH - viewH + 16, 300)
                }

                // 执行附加效果
                if (message.el.flag) {
                    var flag = message.el.flag;

                    switch (flag) {
                        case 'animate-tour':
                            playTour();
                            break;
                        default:
                            break;
                    }
                }

                // 特别语句的特殊delay
                if (message.el.pause != undefined) {
                    loop(message.el.pause);
                } else {
                    loop();
                }

                // 指向下一句
                message = message._next;

            } else {
                activeInput();
                clearTimeout(timeout);
                cb && cb();
            }
        }, delay);
    };

    loop(0);
};

var webSocket = null;
function start() {
    var sendContent = $("#sendContent").val();
    //向服务器发送请求
    webSocket.send(sendContent);
}

function guid() {
    var uid = Math.floor(Math.random()*10+1);
    var nickNames = ['tr','rtk','zsj','zk','lzh','lhq','yy','xc','nn','xb'];
    return nickNames[uid-1];
}

function connect() {


    var token = guid();
    webSocket = new WebSocket('ws://localhost:8080/websocket/'+token);
    setConnected(true);
    webSocket.onerror = function(event) {
        alert(event.data);
    };
    //与WebSocket建立连接
    webSocket.onopen = function(event) {
        document.getElementById('response').innerHTML += '<br />'+ '与服务器端建立连接';
    };
    //处理服务器返回的信息
    webSocket.onmessage = function(event) {
        var message = event.data;
        if ("offline" == message){
            alert("你被踢下线了，哈哈哈！");
            disconnect();
            window.location.href = '/login.html';
        }

    };
}

function disconnect() {
    webSocket.close();
    setConnected(false);
    console.log("与服务器断开连接");
}

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    $("#response").html();
}



function getMe() {
    
}

$(document).ready(function() {

    _members.me = me;
    geneDialog(_userName);

    //展示默认的引导对话
    showDialog(_dialog['d0'], function() {
        $('.J_noticeInput').show();
    });

    // 不同选项点击触发不同的对话和下级选项
    $('.box_ft').on('click', '.J_choice .J_liNext', function(e) {
        e.preventDefault();
        var $choice = $(this);
        var dialogId = $choice.attr('data-target-dialog');
        var choiceId = $choice.attr('data-target-choice');
        var continueDialog = ($choice.attr('data-continue') !== 'false');

        // if ($choice.hasClass('disabled')) {
        // 	return;
        // }
        $('.J_mainChoice').find('.J_liNext[data-target-dialog="' + dialogId + '"]').addClass('disabled');

        if (!choiceId) {
            choiceId = '0';
        }

        hideChoice();
        clearTimeout(window.waitUser);

        showDialog(_dialog['d' + dialogId], function() {
            if (continueDialog) {
                showChoice(choiceId, 500);
                // 用户若干秒没操作的话，提示文案
                window.waitUser = setTimeout(function() {
                    var random = Math.floor((Math.random() * 3) + 1);
                    showDialog(_dialog['dr_' + random]);
                }, 30000);
            }
        });

        if (!$('.J_mainChoice').find('.J_liNext').not('.disabled')) {
            clearTimeout(window.waitUser);
        }
    });

    //显示或者隐藏菜单
    $(document).on('click', '.J_inputWrapper', function() {
        var choosen = $('.J_choice').filter('.choosen').attr('data-choice');
        if ($('.J_choiceWrapper').hasClass('opened')) {
            hideChoice();
        } else {
            showChoice(choosen, 0);
        }
    });

    // 对话里的图片点击时会全屏显示
    $(document).on('click', '.J_img', function(e) {
        var $target = $(this);
        var imgUrl = $target.attr('src').replace(/\.(jpg|jpeg|png|gif)/, '-hd.$1');

        if (imgUrl) {
            // 全屏显示照片
            loadImg(imgUrl);
            $('#J_fullPics').show();
        }

    });
});