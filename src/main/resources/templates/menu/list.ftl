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
        <label class="layui-form-label">菜单名:</label>
        <div class="layui-input-inline">
            <input class="layui-input" name="menu_name" autocomplete="off">
        </div>
        <label class="layui-form-label">菜单类型:</label>
        <div class="layui-input-inline" name="menu_type" combobox = "dicDefine:'menuType',filter:'menuType'">
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
    <@shiro.hasPermission name="menu:add">
        <button class="layui-btn" data-type="add"><i class="fa fa-plus">&nbsp;</i>增加</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="menu:update">
        <button class="layui-btn" data-type="edit" style="margin-left: 5px!important;"><i class="fa fa-pencil-square-o">&nbsp;</i>修改</button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="menu:del">
        <button class="layui-btn" data-type="del" style="margin-left: 5px!important;"><i class="fa fa-trash-o">&nbsp;</i>删除</button>
    </@shiro.hasPermission>
</div>

<table id="menu-list" lay-filter="menu-list"></table>
</div>
<script type="text/html" id="typeTpl">
    {{# if(d.menuType === '0'){ }}
        <span class="layui-btn layui-btn-xs">目录</span>
    {{# } else if(d.menuType === '1'){ }}
        <span class="layui-btn layui-btn-warm layui-btn-xs">菜单</span>
    {{# } else if(d.menuType === '2'){ }}
        <span class="layui-btn layui-btn-danger layui-btn-xs">按钮</span>
    {{# } }}
</script>
<script type="text/html" id="iconTpl">
    {{#  if(d.menuIcon != null){ }}
    {{#  if(d.menuIcon.indexOf("fa-") !== -1){ }}
        <i class="fa {{d.menuIcon}}"></i>
    {{# }else{ }}
        <i class="layui-icon">{{d.menuIcon}}</i>
    {{#  } } else { '' } }}
</script>
<script src="/menu/menu.js"></script>
</body>
</html>