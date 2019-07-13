function changeV(v) {
    var text = $(v).text();
    $("#search_type_name").html(text);
    switch (text) {
        case "shop":
            $("#search_type").val(1);
            break;
        case "Product":
            $("#search_type").val(2);
            break;
    }
    $('#search_productName').focus()
}

function btn_search(){
    var searchType = $("#search_type").val();
    var searchProductName = $("#search_productName").val();
    if (searchType == 2) {
        window.location.href = "/product/search?&searchProductName=" + searchProductName;
    } else if (searchType == 1) {
        window.location.href = "/shop/getList?&shopName=" + searchProductName;
    }
}

$(".tog_in").find('li').hover(function () {
    $(this).css("background-color", "#F58022");
}, function () {
    $(this).css("background-color", "");
});

