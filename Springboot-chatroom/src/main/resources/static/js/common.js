/**
 * 构建user对象
 * @param username 用户名
 * @param password 密码
 * @returns user对象
 */
function createUser(username, password) {
    let o = {};
    o.username = username;
    o.password = password;
    return o;
}

/**
 * tip提示框
 * @param p 要绑定的标签
 * @param msg 显示的信息
 */
function showTip(p, msg) {
    p.popover({
        content: msg, //显示的信息
        placement: 'right', //显示问题
        trigger: 'manual' //显示模式，设置为js控制
    });
    //显示方法
    p.popover('show');
    //显示之后定时关闭
    setTimeout(() => {
        p.popover('hide');
        p.removeData('bs.popover');
    }, 3000);
}

