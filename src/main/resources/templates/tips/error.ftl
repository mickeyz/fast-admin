<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>error</title>
    <#include "../resource.ftl"/>
    <style>
        .tips {
            margin-top: 30px;
            text-align: center;
        }

        .tips .layui-icon[face] {
            display: inline-block;
            font-size: 300px;
            color: #393D49
        }

        .tips .layui-text {
            width: 500px;
            margin: 30px auto;
            padding-top: 20px;
            border-top: 5px solid #009688;
            font-size: 16px;
        }

        .msg {
            width: 500px;
            margin: 30px auto;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="layui-fluid">
    <div class="tips">
        <i class="layui-icon layui-icon-face-cry" face=""></i>
        <div class="layui-text" style="font-size: 20px;">
            好像出错了呢
        </div>
    </div>
    <div class="msg">
        <#if msg??>
            <fieldset class="layui-elem-field">
                <legend>Error Tips</legend>
                <div class="layui-collapse" lay-filter="test" style="margin-top: 20px">
                    <div class="layui-colla-item">
                        <h2 class="layui-colla-title">Msg</h2>
                        <div class="layui-colla-content layui-show">
                            ${msg}
                        </div>
                    </div>
                    <div class="layui-colla-item">
                        <h2 class="layui-colla-title">Url</h2>
                        <div class="layui-colla-content layui-show">
                            ${url}
                        </div>
                    </div>
                </div>
            </fieldset>
        </#if>
    </div>
</div>
</body>
<script>
    layui.use(['element', 'layer'], function () {
        var element = layui.element();
        var layer = layui.layer;

        //监听折叠
        element.on('collapse(test)', function (data) {
            layer.msg('展开状态：' + data.show);
        });
    });
</script>
</html>