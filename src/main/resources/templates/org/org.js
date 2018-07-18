layui.use(['table','form'], function () {
    var table = layui.table
        , $ = layui.$
        , form = layui.form;

    table.render({
        elem: '#org-list'
        , type: 'post'
        , url: '/sys/org/listData' //数据接口
        , page: true //开启分页
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'orgCode', title: '单位编码', sort: true}
            , {field: 'orgName', title: '单位名称', sort: true}
            , {field: 'orgPname', title: '上级单位', sort: true}
            , {field: 'statue', title: '使用状态', templet: '#switchTpl'}
        ]]
        , id: 'orgTable' // 容器唯一ID
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
                table.reload('orgTable', {
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
                , title: '添加单位'
                , content: '/sys/org/add'
                , area: ['700px', '500px']
                , maxmin: true  //开启最大化最小化按钮
            });
        }
        // 修改
        , edit: function () {
            // 获取选中的数据
            var checkStatus = table.checkStatus('orgTable');
            var data = getTableRows(checkStatus, 1);
            if (data) {
                layer.open({
                    type: 2
                    , title: '修改单位'
                    , content: '/sys/org/add'
                    , area: ['700px', '500px']
                    , maxmin: true
                    , success: function (layero, index) {
                        var iframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象
                        iframeWin.parentParas(data);// 给ifram传值
                    }
                })
            }
        }
        // 删除
        , del: function () {
            // 获取选中的数据
            var checkStatus = table.checkStatus('orgTable');
            var data = getTableRows(checkStatus);
            if (data) {
                layer.confirm('确定删除记录?', function (index) {
                    $.ajax({
                        type: "POST",
                        url: "/sys/org/del",
                        data: JSON.stringify(checkStatus.data),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (result) {
                            if (result.code === 0) {
                                layui.table.reload('orgTable',{page: {curr: 1}});
                                layer.msg("操作成功!", {icon: 1});
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