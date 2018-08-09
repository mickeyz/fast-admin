<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<form class="layui-form" lay-filter="role-grant" style="padding: 20px 0">
    <input id="roleType" value ="${RequestParameters["roleType"]}" hidden/>
    <input id="roleUserTemp" value ="${RequestParameters["roleUserTemp"]}" hidden/>
    <input id="roleMenuTemp" value ="${RequestParameters["roleMenuTemp"]}" hidden/>
    <div class="layui-form-item" id="item1">
        <div class="layui-inline">
            <select name="roleUser" id="roleUser" xm-select="roleUser" xm-select-show-count="3">
                <option value="">请选择用户</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item" id="item2">
        <div class="ztree" id="roleMenu"></div>
    </div>
</form>
<script>
    layui.use('form', function() {
        var form = layui.form
                , $ = layui.$
                , formSelects = layui.formSelects;

        // 显示表单切换
        if ($('#roleType').val() === '1') {
            $('#item1').show();
            $('#item2').hide();
        } else {
            $('#item1').hide();
            $('#item2').show();
        }

        // 多选下拉框 获取用户信息
        formSelects.data('roleUser', 'server', {
            url: '/sys/role/user',
            beforeSuccess: function(id, url, searchVal, result){
                return result.data;
            }
            , success:function () {
                // 赋值
               formSelects.value('roleUser',$('#roleUserTemp').val().split(","));
                // 默认展开
               $('.xm-select-sj').click();

            }
        });

        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        // 菜单树
        var ztree;
        if ($('#roleType').val() === '2') {
            $.ajax({
                type: "POST",
                url: "/sys/role/menu",
                dataType: "json",
                success: function (result) {
                    if (result.code === 0) {
                        ztree = $.fn.zTree.init($("#roleMenu"), setting, result.data);
                        var _value = $('#roleMenuTemp').val().split(",");
                        for (var i = 0; i < _value.length; i++) {
                            ztree.checkNode(ztree.getNodeByParam("id", _value[i]), true, false);
                        }
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            });
        }

        // 提交
        window.roleGrent = function (data, type){
            var roleUserSel = formSelects.value('roleUser', 'val');
            var roleMenuTemp = $('#roleMenuTemp').val();
            var flag = false;
            var params;
            var n_id = "";
            if (JSON.stringify(data.roleUser.split(",").sort()) != JSON.stringify(roleUserSel.sort()) && type === 'user') {
                flag = true;
                params = "roleId=" + data.roleId + "&roleUser=" + roleUserSel;
            }
            if (n_id != "" && JSON.stringify(n_id.split(",").sort()) != JSON.stringify(roleMenuTemp.split(",").sort()) && type === 'menu') {
                var node = ztree.getCheckedNodes(true);
                for (var i = 0; i < node.length; i++) {
                    n_id = i > 0 ? n_id + "," + node[i].id : n_id + node[i].id;
                }
                flag = true;
                params = "roleId=" + data.roleId + "&roleMenu=" + n_id;
            }
            if (flag){
                var url = type === 'user' ? '/sys/role/userGrant' : '/sys/role/menuGrant';
                $.ajax({
                    type: "POST",
                    url: url,
                    data: params,
                    dataType: "json",
                    success: function (result) {
                        if (result.code === 0) {
                            layer.msg("操作成功!", {icon: 1}
                            , function(){
                                // 关闭窗口并刷新列表
                                var  frameindex= parent.layer.getFrameIndex(window.name);
                                parent.layer.close(frameindex);
                                parent.layui.table.reload('roleTable'); // 父页面table容器唯一id
                            });
                            return true;
                        } else {
                            layer.msg(result.msg, {icon: 5});
                            return false;
                        }
                    }
                });
            }
            return flag;
        }

    });
</script>
</body>
</html>