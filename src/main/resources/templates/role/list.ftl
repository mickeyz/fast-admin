<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../resource.ftl"/>
</head>
<body>
<div class="layui-fluid">
<div class="searchTable" style="padding: 20px">
    <form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称:</label>
        <div class="layui-input-inline">
            <input class="layui-input" name="role_name" autocomplete="off">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn search-btn" lay-submit lay-filter="reload" data-type="reload"><i class="fa fa-search">&nbsp;</i>搜索</button>
            <button class="layui-btn layui-btn-primary" type="button" data-type="reset"><i class="fa fa-refresh">&nbsp;</i>重置</button>
        </div>
    </div>
    </form>
</div>
<div class="layui-btn-group">
    <!--使用shiro标签进行按钮权限验证-->
    <@shiro.hasPermission name="role:add">
        <button class="layui-btn" data-type="add"><i class="fa fa-plus">&nbsp;</i>增加</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="role:update">
        <button class="layui-btn" data-type="edit" style="margin-left: 5px!important;"><i class="fa fa-pencil-square-o">&nbsp;</i>修改</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="role:del">
        <button class="layui-btn" data-type="del" style="margin-left: 5px!important;"><i class="fa fa-trash-o">&nbsp;</i>删除</button>
    </@shiro.hasPermission>
</div>

<table id="role-list" lay-filter="role-list"></table>
</div>
<script type="text/html" id="barDemo">
    <@shiro.hasPermission name="role:update">
        <a class="layui-btn layui-btn-normal layui-btn-xs layui-icon layui-icon-user" lay-event="userGrant"> 用户授权</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs layui-icon layui-icon-util" lay-event="menuGrant"> 菜单授权</a>
    </@shiro.hasPermission>
</script>
<script type="text/html" id="switchTpl">
    <!-- checked状态 -->
    <input type="checkbox" disabled name="roleStatus" value="{{d.roleStatus}}" lay-skin="switch" lay-text="使用中|已注销" lay-filter="statue" {{ d.roleStatus == 1 ? 'checked' : '' }}>
</script>
<script src="/role/role.js"></script>
</body>
</html>