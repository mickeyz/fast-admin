<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<form class="layui-form" lay-filter="user-add" style="padding: 20px 0">
    <input name="id" hidden>
    <div class="layui-form-item">
        <label class="layui-form-label">登录ID：</label>
        <div class="layui-input-inline">
            <input name="loginid" lay-verify="required" autocomplete="off" placeholder="请输入帐号" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">用户名称：</label>
        <div class="layui-input-inline">
            <input name="username" lay-verify="required" autocomplete="off" placeholder="请输入用户名" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">电话：</label>
        <div class="layui-input-inline">
            <input name="telphone" lay-verify="required|phone" autocomplete="off" placeholder="请输入电话" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">邮箱：</label>
        <div class="layui-input-inline">
            <input name="email" lay-verify="required|email" autocomplete="off" placeholder="请输入邮箱" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属单位：</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="orgname" name="orgname" placeholder="请选择单位" inputTree="url:'/sys/org/tree',name:'orgid'" type="text"/>
        </div>
        <label class="layui-form-label">使用状态：</label>
        <div class="layui-input-inline" combobox = "dicDefine:'isuse'" name="statue" value="1">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">地址：</label>
        <div class="layui-input-block">
            <input name="addr" lay-verify="" autocomplete="off" placeholder="请输入地址" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-block">
            <textarea class="layui-textarea" name="memo" autocomplete="off"></textarea>
        </div>
    </div>
    <div class="layui-form-item" align="center">
        <button class="layui-btn" lay-submit lay-filter="save">保存</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
<script>
layui.use('form', function() {
    var form = layui.form, $ = layui.$;
    // 保存
    form.on('submit(save)', function(data){
        var url = data.field.id === '' ? '/sys/user/save' : '/sys/user/update';
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data.field),
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                if (result.code === 0) {
                    layer.msg("操作成功!", {icon: 1}
                    , function(){
                        // 关闭窗口并刷新列表
                        var  frameindex= parent.layer.getFrameIndex(window.name);
                        parent.layer.close(frameindex);
                        parent.layui.table.reload('userTable'); // 父页面table容器唯一id
                    });
                } else {
                    layer.msg(result.msg, {icon: 5});
                }
            }
        });
        return false;
    });

    // 获取父页面传递的值并赋值给form
    window.parentParas = function (data) {
        form.val('user-add',JSON.parse(JSON.stringify(data)));
    }
});
</script>
</body>
</html>