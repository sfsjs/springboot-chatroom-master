//当打开页面时新建WebSocket连接
const host = window.location.host,
    url = "ws://" + host + "/chat",//访问到后台项目注解所在的类
    webSocket = new WebSocket(url); //new一个新ws对象，new完即新建立一条“管道”

webSocket.onopen = function () {
    console.log("连接成功！");
};

window.onbeforeunload = function () {
    webSocket.close();
}

webSocket.onmessage = function (res) {
    showOnLine();
    // console.log('WebSocket收到消息：' + res.data);
    //获取服务端消息
    let message = JSON.parse(res.data) || {};
    let msg;
    if (message.type === "连接") {
        msg = `<font color='#CC0000'>${message.content}</font>
                （${message.sendTime}）<br>
              `
    } else if (message.type === "聊天"){
         msg = `<font color='blue'><strong>${message.from}</strong></font>
                <font color='#CC0000'>${message.face}</font>对
                <font color='green'>[${message.to}]</font>说：
                <font color='${message.color}'>${message.content}</font>
                （${message.sendTime}）<br>
              `
    }
    showContent(msg);
}

webSocket.onerror = function (err) {
    console.log('WebSocket收到错误消息：', err);
}

// 显示在线人员列表
function showOnLine() {
    // 异步发送请求 获取在线人员列表
    // Jquery发送异步请求
    $.ajax({
        url: "/online",
        contentType: "application/json;charset=UTF-8",
        success(res) {
            $(".online-content").html(res);
            // console.log("渲染");
        }
    })
}

// 显示聊天的内容
function showContent(msg) {
    let $content = $("#content");
    let content = $content.html();
    $content.html(content + msg);
}


function set(selectPerson) {	//自动添加聊天对象
    let to = $('input[name=to]')
    if (selectPerson !== $('.user-username').text()) {
        to.val(selectPerson);
    } else {
        // alert("请重新选择聊天对象！");
        // 跳转到修改个人信息页
        window.open("/userInfo");
    }
}

$('.SubmitsSend').on("click", () => {
    send();
})

function send() {
    let to = $('input[name=to]')
    let content = $('input[name=content]')
    if (to.val() === "") {
        alert("请选择聊天对象！");
        return;
    }
    if (content.val() === "") {
        alert("发送信息不可以为空！");
        content.on('focus', () => {
        });
        return;
    }
    // $("#form1").serialize():让表单中所有的元素都提交.
    webSocket.send(JSON.stringify($("#chat-form").serializeObject()));

    $("input[name='content']").val("").on('focus', () => {
    })
}

function exit() {
    webSocket.close();
    window.location.href = "/user/logout";
}

/**
 * 按回车发送消息
 */
$('input[name="content"]').on('keypress', (event) => {
    if (event.key === 'Enter') {
        // console.log("发送消息");
        send();
    }
})

/**
 * 序列化form表单，例如{id:1,name:aa,age:25}
 * 注意：form表单的每个input都必须要有name
 */
$.fn.serializeObject = function () {
    let o = {};
    let a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    o['sendTime'] = new Date().Format("yyyy-MM-dd hh:mm:ss");
    return o;
};

// 将日期格式化...将格式化加载这个函数的原型上....
Date.prototype.Format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
