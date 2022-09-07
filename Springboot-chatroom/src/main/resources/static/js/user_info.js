$('button').on('click', () => {
    let id = $('#id').val();
    let $username = $('#username');
    let $phone = $('#phone');
    let username = $username.val().trim();
    let phone = $phone.val().trim();

    if (username === "") {
        showTip($username, "用户名不能为空");
        return;
    }
    if (phone === "") {
        showTip($phone, "手机号不能为空");
        return;
    }
    if (!/^1[345789]\d{9}$/.test(phone)) {
        showTip($phone, "手机号格式错误");
        return;
    }

    $.ajax({
        method: "POST",
        url: "/user",
        data: JSON.stringify(createUser(id, username, phone)),
        contentType: "application/json;charset=UTF-8",
        success(res) {
            alert(res);
        },
        error(err) {
            showTip($username, err.responseText);
        }
    })
})

/**
 * 构建user对象
 * @param id 用户id
 * @param username 用户名
 * @param phone 用户手机号
 * @returns user对象
 */
function createUser(id, username, phone) {
    let o = {};
    o.id = id;
    o.username = username;
    o.phone = phone;
    return o;
}