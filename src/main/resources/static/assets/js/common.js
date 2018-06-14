function timestampToTime(timestamp) {
    if(timestamp.length == 10){
        timestamp = timestamp * 1000;//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    }
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

function scrollToBottom(id) {
    var scrollHeight = $('#'+id).prop("scrollHeight");
    console.log(scrollHeight);
    $('#'+id).scrollTop(scrollHeight,200);
}

function isNull( str ){
    if ( str == "" ) return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
}

function getCurrentTimestamp() {
    return Date.parse(new Date());
}
