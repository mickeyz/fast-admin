(function ($) {
    /** 入口函数 */
    $.fn.comboboxTool = function () {
        //  当前对象
        var $grid = this;
        //  处理存在属性为combobox的对象
        combobox = $grid.attr("combobox");
        if (!combobox) {
            return
        }
        combobox = combobox ? combobox : "";
        //  将对象转为json
        combobox = eval("({" + combobox + "})");
        //  根据字典定义获取数据
        var define = combobox.dicDefine;
        //  自定义url获取数据
        var url = combobox.url;
        var R = "";
        if (define != undefined && define != "") {
            R = selectTool.getDataByDefine(combobox.dicDefine);
        }
        if (url != undefined && url != "") {
            R = selectTool.getDataByUrl(combobox.url);
        }
        selectTool.renderData(R, $grid, combobox);
    };
    /**  默认配置 */
    var combobox = {};
    /** 方法对象 */
    var selectTool = {
        /** 通过define获取数据*/
        getDataByDefine: function (dicDefine) {
            $.ajax({
                url: $s.getDicByDefine,
                async: false,
                data: {dicdefine: dicDefine},
                type: 'post',
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /** 通过url获取数据 */
        getDataByUrl: function (url) {
            var data;
            $.ajax({
                url: url,
                async: false,
                dataType: "json",
                success: function (R) {
                    if (R.code == 0) {
                        data = R;
                    } else {
                        data = {};
                        alert(R.msg);
                    }
                }
            });
            return data;
        },
        /** 渲染下拉框数据 */
        renderData: function (R, $grid, combobox) {
            var _grid = $grid;
            // 获取下拉控件的name
            var _name = $(_grid).attr("name");
            // 获取下拉控件的默认值
            var _value = $(_grid).attr("value");
            // 获取需要验证的参数
            var _verify = $(_grid).attr("lay-verify") || "";
            $(_grid).removeAttr("name");
            $(_grid).removeAttr("value");
            $(_grid).removeAttr("lay-verify");
            // 获取是否有提示
            var _selectTip = combobox.tips || "";
            // 获取监控标识
            var filter = combobox.filter || "";
            // 搜索功能参数
            var _search = combobox.search || "true";
            var data = R.data;
            var _select = '<select name="' + _name + '" ></select>';
            // 是否开启搜索功能
            if (_search == "true") {
                _select = _select.replace("<select", "<select  lay-search");
            }
            // 添加监控标识
            if (filter != "") {
                _select = _select.replace("<select", "<select lay-filter=" + filter);
            }
            // 验证值
            if (_verify != undefined && _verify != "") {
                _select = _select.replace("<select", "<select  lay-verify='" + _verify + "'");
            }
            $(_grid).append(_select);
            if (_selectTip != "false") {
                $(_grid).find("select").append('<option value="">' + _selectTip + '</option>');
            }
            // 设置值
            if (data != undefined) {
                for (var i = 0; i < data.length; i++) {
                    var _option = '<option value="' + data[i].code + '">' + data[i].value + '</option>';
                    //设置默认值
                    if (_value == data[i].code) {
                        _option = _option.replace("<option", "<option selected ")
                    }
                    $(_grid).find("select").append(_option);
                }
            }
        }
    }

})(jQuery);

/** 初始化*/
$(document).ready(function () {
    var selects = $("[combobox]");
    for (var i = 0; i < selects.length; i++) {
        $(selects[i]).comboboxTool();
    }
});