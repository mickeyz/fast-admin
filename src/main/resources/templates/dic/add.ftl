<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<form class="layui-form" lay-filter="dic-add" style="padding: 20px 0">
    <input name="id" hidden>
    <div class="layui-form-item">
        <label class="layui-form-label">字典定义：</label>
        <div class="layui-input-inline">
            <input name="dicdefine" lay-verify="required" autocomplete="off" placeholder="请输入字典定义名" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">字典描述：</label>
        <div class="layui-input-inline">
            <input name="dicdesc" lay-verify="required" autocomplete="off" placeholder="请输入字典描述" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字典编码：</label>
        <div class="layui-input-inline">
            <input name="diccode" lay-verify="required" autocomplete="off" placeholder="请输入字典编码" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">字典名称：</label>
        <div class="layui-input-inline">
            <input name="dicname" lay-verify="required" autocomplete="off" placeholder="请输入字典名称" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">使用状态：</label>
        <div class="layui-input-inline" combobox = "dicDefine:'isuse'" name="isuse" value="1">
        </div>
    </div>
    <div class="layui-form-item" align="center">
        <button class="layui-btn" lay-submit lay-filter="save">保存</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
<script>
    layui.use('form', function() {
        var form = layui.form
                , $ = layui.$;

        // 保存
        form.on('submit(save)', function(data){
            var url = data.field.id === '' ? '/sys/dic/save' : '/sys/dic/update';
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
                                    parent.layui.table.reload('dicTable'); // 父页面table容器唯一id
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
            form.val('dic-add',JSON.parse(JSON.stringify(data)));
        }

    });
</script>
</body>
</html>