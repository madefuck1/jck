function changeV(v) {
    var text = $(v).text();
    $("#search_type_name").html(text);

    switch (text) {
        case "产品":
            $("#search_type").val(1);
            break;
        case "店铺":
            $("#search_type").val(2);
            break;
    }
    $('#search_productName').focus()
}

function btn_search(){
    var searchType = $("#search_type").val();
    var searchProductName = $("#search_productName").val();
    if (searchType == 1) {
        window.location.href = "/product/search?&searchProductName=" + searchProductName;
    } else if (searchType == 2) {
        window.location.href = "/shop/getList?&shopName=" + searchProductName;
    }
}
// 按Enter键,执行搜索事件
$('#search_productName').bind('keypress', function (e) {
    var keyCode = null;
    if(e.which)
        keyCode = e.which;
    else if(e.keyCode)
        keyCode = e.keyCode;
    if (keyCode == "13") {
        btn_search();
    }
});

$(".tog_in").find('li').hover(function () {
    $(this).css("background-color", "#F58022");
}, function () {
    $(this).css("background-color", "");
});


