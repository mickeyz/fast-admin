// 判断table选中值
function getTableRows(obj, arr) {
    if (arr === 1) {
        if (obj.data.length === 0 || obj.data.length > 1) {
            layer.msg("请选择一条数据!", {icon: 5});
            return false;
        }
    } else {
        if (obj.data.length === 0) {
            layer.msg("请选择一条数据!", {icon: 5});
            return false;
        }
    }
    return obj.data[0];
}
// 定义全局变量 文件下载
jQuery.download = function (url, method, id) {
    jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +
        '<input type="text" name="id" value="' + id + '"/>' +
        '</form>')
        .appendTo('body').submit().remove();
};