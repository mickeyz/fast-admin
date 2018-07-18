$('dl.skin > dd').on('click', function () {
    var $that = $(this);
    var skin = $that.children('a').data('skin');
    switchSkin(skin);
});
var setSkin = function (value) {
        layui.data('kit_skin', {
            key: 'skin',
            value: value
        });
    },
    getSkinName = function () {
        return layui.data('kit_skin').skin;
    },
    switchSkin = function (value) {
        var _target = $('link[kit-skin]')[0];
        _target.href = _target.href.substring(0, _target.href.lastIndexOf('/') + 1) + value + _target.href.substring(_target.href.lastIndexOf('.'));
        setSkin(value);

    },
    initSkin = function () {
        var skin = getSkinName();
        switchSkin(skin === undefined ? 'default' : skin);
    }();