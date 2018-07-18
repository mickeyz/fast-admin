layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;

    //登录按钮事件
    form.on("submit(login)", function (data) {
        var params = "username=" + data.field.username + "&password=" + data.field.password + "&captcha=" + data.field.captcha;
        $.ajax({
            type: "POST",
            url: "/sys/login",
            data: params,
            dataType: "json",
            success: function (result) {
                if (result.code === 0) {
                    layer.msg(result.msg, {
                        icon: 1
                        , time: 1000
                    }, function () {
                        location.href = '/index'; //后台主页
                    });
                } else {
                    layer.msg(result.msg, {icon: 5});
                    refreshCode();
                }
            }
        });
        return false;
    });
});
function refreshCode(){
    var captcha = document.getElementById("captcha");
    captcha.src = "/captcha.jpg?t=" + new Date().getTime();
}
