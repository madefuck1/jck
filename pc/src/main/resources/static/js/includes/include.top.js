//ajax请求 购物车数量变更后/推送信息更新后 调用刷新top
function initIncludeTop(request) {
    var isLonIn = request.getResponseHeader('isLonIn');
    var carCount = request.getResponseHeader('shopCarCount');
    if (isLonIn == "Y") {
        var pushCount = request.getResponseHeader('pushInfoCount');
        var userPhone = request.getResponseHeader('userPhone');
        var html = "";
        html += '<p>欢迎您 &nbsp;<a href="javascript">' + userPhone + '</a>' +
            ' &nbsp;<a href="javascript">消息(' + pushCount + ')</a></p>'

        // 刷新页面
        $('#html_top').html("");
        $('#html_top').append(html);
        // 购物车数量
        $('#carCount').text("(" + carCount + ")");
    }
}

