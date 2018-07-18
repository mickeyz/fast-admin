(function ($) {
    var inputTree = {};
    /* 入口函数 */
    $.fn.inputTreeTool = function () {
        //参数数据
        inputTree = $(this).attr("inputTree");
        if (!inputTree) {
            return
        }
        inputTree = inputTree ? inputTree : "";
        //将参数转为json
        inputTree = eval("({" + inputTree + "})");
        //添加默认样式
        $(this).attr("readonly", "readonly");
        $(this).attr("style", "padding-right: 30px;");
        //添加清空按钮
        $(this).after('<i class="layui-icon layui-icon-close clear-btn" onclick="clearTreeData(this)"></i>');
        //获取控件id
        var _id = $(this).attr("id")||"id_"+new Date().getTime();
        //获取下拉树默认值的id
        var _value = $(this).attr("value") || "";
        //下拉树显示的值
        var valueName="";
        // //加载下拉树数据
        // $.ajax({
        //     type: "post",
        //     url: inputTree.url,
        //     contentType: "application/json",
        //     async: false,
        //     dataType: "json",
        //     success: function (R) {
        //         if (R.code == 0) {
        //             ztree = $.fn.zTree.init($("#zTree"), setting, R.data);
        //             var node = ztree.getNodeByParam("id", _value);
        //             if (node != null) {
        //                 //获取下拉树要显示的值
        //                 valueName=node.name;
        //                 // 选中下拉树默认节点
        //                 ztree.selectNode(node);
        //                 $(this).val(node.name);
        //             }
        //         } else {
        //             alert(R.msg);
        //         }
        //     },
        //     error: function () {
        //         alert("系统错误");
        //     }
        // });
        if(_value!=null&&_value!=""){
            $(this).attr("valueId",_value);
            $(this).val(valueName);
        }

        $("#" + _id + "_id").remove();
        //$("#treeLayer").remove();
        $(".layui-layer-molv").remove();

        $(this).after('<input value="' + _value + '"  style="display: none" id="' + _id + '_id"  name="' + inputTree.name + '"  class="layui-input">' +
            '<div id="treeLayer" style="display: none;padding:10px;"> ' +
            '<ul id="zTree" class="ztree"></ul> ' +
            '</div>');
    };


})(jQuery);
var ztree;
/**菜单列表*/
function openZtree(obj) {
    var inputTree = $(obj).attr("inputTree");
    if (!inputTree) {
        return;
    }
    inputTree = inputTree ? inputTree : "";
    //将参数转为json
    inputTree = eval("({" + inputTree + "})");
    var _check = inputTree.check != undefined ? inputTree.check : false;
    var setting = {
        check: {
            enable: _check
        },
        data: {
            simpleData: {
                enable: true
            },
            key: {
                url: "nourl"
            }
        }
    };
    var _id = $(obj).attr("id")||"id_"+new Date().getTime();
    var _value=$("#" + _id + "_id").val();
    $("#" + _id + "_id").remove();
    $(".layui-layer-molv").remove();
    $("#treeLayer").remove();
    $(obj).after('<input  id="' + _id + '_id"  style="display: none" name="' + inputTree.name + '" value="' + _value + '" class="layui-input">' +
        '<div id="treeLayer" style="display: none;padding:10px;"> ' +
        '<ul id="zTree" class="ztree"></ul> ' +
        '</div>');
    layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "请选择",
        area: ['300px', '400px'],
        shade: 0,
        shadeClose: false,
        content: jQuery("#treeLayer"),
        btn: ['确定', '取消'],
        success: function(){
            $.ajax({
                type: "post",
                url: inputTree.url,
                contentType: "application/json",
                async: false,
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        ztree = $.fn.zTree.init($("#zTree"), setting, R.data);
                        _value = _value.split(",");
                        for (var i = 0; i < _value.length; i++) {
                            var node = ztree.getNodeByParam("id", _value[i]);
                            if (node != null && _check === 'true') {
                                ztree.checkNode(node,true,false);
                            }else{
                                ztree.selectNode(node);
                            }
                        }
                    } else {
                        alert(R.msg);
                    }
                },
                error: function () {
                    alert("系统错误");
                }
            });
        },
        btn1: function (index) {
            var node = _check === 'true' ? ztree.getCheckedNodes(true) : ztree.getSelectedNodes();
            var n_id = "",n_val = "";
            for (var i = 0; i < node.length; i++) {
                n_id = i > 0 ? n_id + "," + node[i].id : n_id + node[i].id;
                n_val = i > 0 ? n_val + "," + node[i].name : n_val + node[i].name;
            }
            // 顶级目录禁止选择
            if (n_id != 0) {
                $("#" + _id + "_id").val(n_id);
                $("#" + _id).val(n_val);
            }
            //关闭菜单
            layer.close(index);
        }
    });
}
/**清空下拉树数据**/
function clearTreeData(obj){
    //重置显示的值
    $(obj).prev().prev().prev().val("");
    $(obj).prev().prev().val("");
}
$(document).ready(function () {
    // 下拉树查询
    var treeTools = $("[inputTree]");
    treeTools.click(function () {
        var obj = $(this);
        openZtree(obj);
    });
    for (var i = 0; i < treeTools.length; i++) {
        $(treeTools[i]).inputTreeTool();
    }


});
