<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<form class="layui-form" lay-filter="menu-add" style="padding: 20px 0">
    <input name="id" hidden>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称：</label>
        <div class="layui-input-inline">
            <input name="menuName" lay-verify="required" autocomplete="off" placeholder="请输入菜单名称" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">菜单类型：</label>
        <div class="layui-input-inline" lay-verify="required" combobox = "dicDefine:'menuType',filter:'menuType'" name="menuType">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单Url：</label>
        <div class="layui-input-inline">
            <input name="menuUrl" autocomplete="off" placeholder="请输入菜单Url" class="layui-input" type="text">
        </div>
        <label class="layui-form-label">排序号：</label>
        <div class="layui-input-inline">
            <input name="menuSort" autocomplete="off" placeholder="请输入排序号" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">父级菜单：</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="menuPname" name="menuPname" inputTree="url:'/sys/menu/tree',name:'menuPid'"
                   placeholder="请选择父级菜单" />
        </div>
        <label class="layui-form-label">授权标识：</label>
        <div class="layui-input-inline">
            <input name="menuParm" autocomplete="off" placeholder="请输入授权标识" class="layui-input" type="text">
        </div>
    </div>
        <div class="layui-form-item">
            <label class="layui-form-label">图标：</label>
            <div class="layui-input-inline">
                <input name="menuIcon" autocomplete="off" placeholder="请输入图标" class="layui-input" type="text">
            </div>
        </div>
    <div class="layui-form-item" align="center">
        <button class="layui-btn" lay-submit lay-filter="save">保存</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
</body>
<script>
    layui.use('form', function() {
        var form = layui.form, $ = layui.$;

        // 监听下拉值
        form.on('select(menuType)', function (data) {
            var _value = data.value;
            if (_value == '0') {
                $('#menuPname').attr("disabled",true);
                $('#menuPname').removeAttr("lay-verify");
                $('#menuPname').val("");
                $("input[name='menuParm']").attr("disabled",true);
                $("input[name='menuParm']").removeAttr("lay-verify");
            }else {
                $('#menuPname').attr("disabled",false);
                $('#menuPname').attr("lay-verify");
                $("input[name='menuParm']").attr("disabled",false);
                $("input[name='menuParm']").removeAttr("lay-verify");
            }
        })

        // 保存
        form.on('submit(save)', function(data){
            var url = data.field.id === '' ? '/sys/menu/save' : '/sys/menu/update';
            if (data.field.menuType !== '0' && data.field.menuPname === '') {
                layer.msg("父级菜单不能为空",{icon: 5});
                return false;
            }
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
                                    parent.layui.table.reload('menuTable'); // 父页面table容器唯一id
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
            form.val('menu-add',JSON.parse(JSON.stringify(data)));
        }
    });
    
</script>
</html>