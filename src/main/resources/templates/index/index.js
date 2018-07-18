var tab;
layui.config({
    base: 'js/kit/',
    version: new Date().getTime()
}).use(['element', 'tab', 'navbar'], function () {
    var $ = layui.jquery,
        navbar = layui.navbar,
        tab = layui.tab;

    //  tab初始化
    tab.set({
        renderType: 'iframe',
        mainUrl: 'main.html',
        openWait: false,
        elem: '#container',
        onSwitch: function (data) { //选项卡切换时触发
        },
        closeBefore: function (data) { //关闭选项卡之前触发
            return true; //返回true则关闭
        }
    }).render();
    // 菜单数据加载，设置远程地址加载
    navbar.set({
        remote: {
            type: 'POST',
            url: '/sys/menu/user?t=' + new Date().getTime()
        }
    }).render(function (data) {
        tab.tabAdd(data);
    });

    $('#baseUser').click(function () {
        layer.open({
            type: 2
            , title: '基本资料'
            , content: '/sys/baseUser'
            , area: ['700px', '500px']
            , maxmin: true
            , shadeClose: true
        })
    });
    $('#updatePwd').click(function () {
        layer.open({
            type: 2
            , title: '密码修改'
            , content: '/sys/updatePwd'
            , area: ['300px', '300px']
            , maxmin: true
            , shadeClose: true
            , btn:['确认','取消']
            , yes: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.updatePwd();
            }
        })
    })
});
$(document).ready(function () {
    $.getJSON("/sys/user/info?_" + $.now(), function (res) {
        if (res.code === 0) {
            $(".username").html(res.msg);
        }
    });
});