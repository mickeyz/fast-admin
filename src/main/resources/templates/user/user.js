layui.use(['table','form'], function () {
    var table = layui.table
        , $ = layui.$
        , form = layui.form;

    table.render({
        elem: '#user-list'
        , type: 'post'
        , url: '/sys/user/listData' //数据接口
        , page: true //开启分页
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'loginid', title: '登录ID', sort: true}
            , {field: 'username', title: '用户名', sort: true}
            , {field: 'telphone', title: '联系电话', sort: true}
            , {field: 'email', title: '联系邮箱', sort: true}
            , {field: 'statue', title: '使用状态', sort: true, templet: '#switchTpl'}
            , {field: 'orgname', title: '所属单位'}
        ]]
        , id: 'userTable' // 容器唯一ID
    });

    //  搜索按钮事件
    $('.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var active = {
        // 执行重载
        reload: function () {
            form.on('submit(reload)', function (data) {
                // 查询条件
                table.reload('userTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: data.field
                });
                return false;
            })
        }
        //  重置
        , reset: function () {
            $(this).parents(".searchTable").find("input").val("");
            $(this).parents(".searchTable").find("select").val("");
        }
        // 新增
        , add: function () {
            layer.open({
                type: 2 //type：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                , title: '添加用户'
                , content: '/sys/user/add'
                , area: ['700px', '500px']
                , maxmin: true  //开启最大化最小化按钮
            });
        }
        // 修改
        , edit: function () {
            // 获取选中的数据
            var checkStatus = table.checkStatus('userTable');
            var data = getTableRows(checkStatus, 1);
            if (data) {
                layer.open({
                    type: 2
                    , title: '修改用户'
                    , content: '/sys/user/add'
                    , area: ['700px', '500px']
                    , maxmin: true
                    , success: function (layero, index) {
                        // var body = layer.getChildFrame('body', index); // 弹出层ifram的body
                        // body.find('#loginid').val("123"); // 给ifram的form赋值
                        var iframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象
                        iframeWin.parentParas(data);// 给ifram传值
                    }
                })
            }
        }
        // 删除
        , del: function () {
            // 获取选中的数据
            var checkStatus = table.checkStatus('userTable');
            var data = getTableRows(checkStatus);
            if (data) {
                layer.confirm('确定删除记录?', function (index) {
                    $.ajax({
                        type: "POST",
                        url: "/sys/user/del",
                        data: JSON.stringify(checkStatus.data),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                layui.table.reload('userTable',{page: {curr: 1}});
                                layer.msg("操作成功!", {icon: 1});
                            } else {
                                layer.msg(result.msg, {icon: 5});
                            }
                        }
                    });
                });
            }
        }
        , resetPwd: function () {
            // 获取选中的数据
            var checkStatus = table.checkStatus('userTable');
            var data = getTableRows(checkStatus);
            if (data) {
                layer.confirm('确定重置密码?', function (index) {
                    $.ajax({
                        type: "POST",
                        url: "/sys/user/reset",
                        data: JSON.stringify(checkStatus.data),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                layer.alert("操作成功,新密码为:123456", {icon: 1});
                            } else {
                                layer.msg(result.msg, {icon: 5});
                            }
                        }
                    });
                });
            }
         }
    };

});