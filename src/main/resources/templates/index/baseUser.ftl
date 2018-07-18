<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/plugins/layui/css/global.css"  media="all">
    <#include "../resource.ftl"/>
    <style>
        body{
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="layui-container">

    <div class="layui-row grid-demo-def" style="text-align: center">
        <div class="layui-col-xs4">
            <img src="/images/admin-mgt.jpg" style="height: 150px;">
        </div>
        <div class="layui-col-xs8">
            <div class="layui-row grid-demo-def">
                <div class="layui-col-xs4"><h1><strong>${user.username!''}</strong></h1></div>
                <div class="layui-col-xs8"></div>
            </div>
            <div class="layui-row grid-demo-def">
                <div class="layui-col-xs2"><i class="fa fa-user"></i>&nbsp;${user.loginid!''}</div>
                <div class="layui-col-xs5" style="text-align: center"><i class="fa fa-envelope"></i>&nbsp;${user.email!'123456@email.com'}</div>
                <div class="layui-col-xs5" style="text-align: center"><i class="fa fa-compass"></i>&nbsp;${user.orgname!'XXX股份有限公司'}</div>
            </div>
        </div>
    </div>
    <div class="layui-row grid-demo-def">
        <div class="layui-col-xs1">&nbsp;</div>
        <div class="layui-col-xs10"><i class="fa fa-phone" style="text-align: left"></i> 手机 : ${user.telphone!'18888888888'}</div>
    </div>
    <div class="layui-row grid-demo-def">
        <div class="layui-col-xs1">&nbsp;</div>
        <div class="layui-col-xs10"><i class="fa fa-automobile "></i> 地址 : ${user.addr!'XXX省(市)XXX区XXX街道XXX小区'}</div>
    </div>
    <div class="layui-row grid-demo-def">
        <div class="layui-col-xs1">&nbsp;</div>
        <div class="layui-col-xs10"><i class="fa fa-text-height"></i> 备注 : <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                ${user.memo!'这人太懒了,什么都没留下...'}
        </div>
    </div>

</div>
</body>
</html>