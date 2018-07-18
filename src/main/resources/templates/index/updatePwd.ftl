<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<form class="layui-form layui-form-pane" action="" style="margin-top: 20px">
    <div class="layui-form-item">
        <label class="layui-form-label">原始密码</label>
        <div class="layui-input-block">
            <input id="opwd" placeholder="请输入原始密码" autocomplete="off" class="layui-input" type="password">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-block">
            <input id="npwd" placeholder="请输入新密码" autocomplete="off" class="layui-input" type="password">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码确认</label>
        <div class="layui-input-block">
            <input id="npwd2" placeholder="请确认新密码" autocomplete="off" class="layui-input" type="password">
        </div>
    </div>
</form>
<script>
    layui.use('form',function () {
        var form = layui.form, $ = layui.jquery;

        window.updatePwd = function () {
            var opwd = $('#opwd').val(),npwd = $('#npwd').val(),npwd2 = $('#npwd2').val();
            if (npwd !== npwd2) {
                layer.msg("新密码不一致,请重新输入!",{icon: 5});
                return;
            }
            $.ajax({
                type: "post",
                url: "/sys/user/updatePwd",
                data: { opwd: opwd ,npwd: npwd },
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        layer.msg("操作成功,请重新登录!", {
                            icon: 1
                            , time: 1000
                        }, function () {
                            parent.location.href = '/logout';
                        });
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            });
        }
    })
</script>
</body>
</html>