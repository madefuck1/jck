
function toPay(){
    var i = 0 , j = 0;
    var html = "";
    $(".shopCar").each(function () {
        var flag = true ;
        $(this).find(".itemCheck").each(function () {
            if($(this).is(":checked")){
                if(flag){
                    html += '<input type="hidden" name="list['+i+'].shopCarId" value="'+$(this).parents(".shopCar").find(".shopCarId").val()+'">' ;
                    flag = false;
                }
                html += '<input type="hidden" name="list['+i+'].shopCarProductIds['+j+']" value="'+$(this).parents(".item-content").find(".shopCarProductId").val()+'">' ;
                j++;
            }
        })
        i++;
    })
    if(html.length == 0){
        alert("请选择商品！");
        return false ;
    }
    $("#toPay").html("");
    $("#toPay").append(html);
    $("#toPay").submit();
    $("#toPay").disable();
}

//切换规格 颜色
function changeProduct(obj){
    var productId = $(obj).parents(".item-content").find(".productId").val();
    var shopCarId = $(obj).parents(".shopCar").find(".shopCarId").val();
    var shopCarProductId =$(obj).parents(".item-content").find(".shopCarProductId").val();
    var productColor = $(obj).parents(".item-content").find(".productColor").text();
    var productSpec = $(obj).parents(".item-content").find(".productSpec").text();
    var price = $(obj).parents('.item-content').find(".price-now").text();
    var productNumber =$(obj).parents(".item-content").find(".productNumber").val();
    var stock = "";
    $.getJSON("/product/getDetail?productId="+productId,function (data) {
        var colorListHtml = '<div class="cart-title">颜色：</div><ul>' ;
        for(var i=0 ; i < data.productColorDtoList.length ; i++){
            if(productColor == data.productColorDtoList[i].productColor){
                colorListHtml += '<li class="sku-line selected" style="cursor: pointer" onclick="checkColor(this,'+data.productColorDtoList[i].spock+')">'+data.productColorDtoList[i].productColor+'</li>';
                stock = data.productColorDtoList[i].spock ;
            }else {
                colorListHtml += '<li class="sku-line" style="cursor: pointer" onclick="checkColor(this,'+data.productColorDtoList[i].spock+')">'+data.productColorDtoList[i].productColor+'</li>';
            }
        }
        colorListHtml += '</ul>';
        $(".colorList").html("");
        $(".colorList").append(colorListHtml);

        var specListHtml = '<div class="cart-title">规格：</div><ul>' ;
        var min = "" , max = "";
        for(var i=0 ; i < data.specDtoList.length ; i++){
            if(productSpec == data.specDtoList[i].specName){
                specListHtml += '<li class="sku-line selected" style="cursor: pointer" onclick="checkSpec(this)">'+data.specDtoList[i].specName+'</li>';
            }else {
                specListHtml += '<li class="sku-line" style="cursor: pointer" onclick="checkSpec(this)">'+data.specDtoList[i].specName+'</li>';
            }
        }
        specListHtml += '</ul>';
        $(".specList").html("");
        $(".specList").append(specListHtml);
        var detail=data.productDetail;
        var url = detail.substring(0, detail.length - 1);
        $(".theme-product").attr("src",url);
        $(".theme-productNumber").val(productNumber);
        $(".theme-productNumber").attr("min",min);
        $(".theme-productNumber").attr("max",max);
        $(".theme-popover").find(".theme-productId").val(productId);
        $(".theme-popover").find(".theme-shopCarProductId").val(shopCarProductId);
        $(".theme-popover").find(".theme-shopCarId").val(shopCarId);
        $(".theme-popover").find(".stock").html(stock);
        $(".theme-popover").find(".price-now").find("em").html(price);
        $(".theme-popover-mask").show();
        $(".theme-popover").show();
    })

}
function checkColor(obj,spock){
    $(obj).parent().find("li").each(function () {
        $(this).removeClass("selected");
    });
    $(obj).addClass("selected");
    $(".theme-popover").find(".stock").html(spock);
}

function checkSpec(obj){
    $(obj).parent().find("li").each(function () {
        $(this).removeClass("selected");
    });
    $(obj).addClass("selected");
}


$(".theme-popover-mask").bind("click",function () {
    $(".theme-popover").hide();
    $(".theme-popover-mask").hide();
})

function closeTheme(){
    $(".theme-popover").hide();
    $(".theme-popover-mask").hide();
}

//全选
$(".allCheck").bind("click",function () {
    if($(this).is(":checked")){
        $(".itemCheck").each(function (){
            $(this)[0].checked = true;
        });
    }else {
        $(".itemCheck").each(function (){
            $(this)[0].checked = false;
        });
    }
    calculate();
})

//店铺所有产品选中
$(".listCheck").bind("click",function () {
    if($(this).is(":checked")){
        $(this).parents(".bundle").find(".itemCheck").each(function () {
            $(this)[0].checked = true;
        });
    }else {
        $(this).parents(".bundle").find(".itemCheck").each(function () {
            $(this)[0].checked = false;
        });
    }
    calculate();
})

//单个产品选中
$(".itemCheck").bind("click",function () {
    calculate();
})

//计算价格
function calculate() {
    var sumPrice = 0;
    var sumNumber = 0 ;
    $(".itemCheck").each(function () {
        if($(this).is(":checked")){
            sumNumber ++ ;
            sumPrice = parseFloat(sumPrice) + parseFloat($(this).parents(".item-content").find(".productSum").html());
        }
    })
    $(".sumNumber").html(sumNumber);
    $(".sumPrice").html(toDecimal2(sumPrice));
}

function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

function toDetail(id) {
    //TODO 跳转到产品详情
    window.location.href='/product/toDetail?productId=' + id
}

function toFavorite(obj) {
    var productId = $(obj).parents(".item-content").find(".productId").val();
    //TODO 移入收藏夹
}

function deleteCheck(){
    var json = "[" ;
    $(".itemCheck").each(function () {
        if($(this).is(":checked")){
            json += $(this).parents(".item-content").find(".shopCarProductId").val()+",";
        }
    })
    json = json.substring(0,json.length-1) + "]";
    $.ajax({
        url: '/shopCar/delete',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data:json,
        success: function (data) {
            $(".itemCheck").each(function () {
                if($(this).is(":checked")){
                    if($(this).parents(".shopCar").find(".item-content").length == 1){
                        $(this).parents(".shopCar").remove();
                    }else {
                        $(this).parents(".item-content").remove();
                    }
                }
            })
            calculate();
        }
    });
}

function deleteCheck(){
    var i = 0 , j = 0;
    var html = "";
    var htmls="";
    $(".shopCar").each(function () {
        var flag = true ;
        $(this).find(".itemCheck").each(function () {
            if($(this).is(":checked")){
                if(flag){
                    html += '<input type="hidden" name="list['+i+'].shopCarId" value="'+$(this).parents(".shopCar").find(".shopCarId").val()+'">' ;
                    flag = false;
                }
                html += '<input type="hidden" name="list['+i+'].shopCarProductIds['+j+']" value="'+$(this).parents(".item-content").find(".shopCarProductId").val()+'">' ;
                htmls+=$(this).parents(".item-content").find(".shopCarProductId").val()+",";
                j++;
            }
        })
        i++;
    })
    if(html.length == 0){
        alert("请选择商品！");
        return false ;
    }else{
        //获取复选框值
        var shopCarProductId=htmls.split(",");
        for(var i = 0 ;i<shopCarProductId.length;i++)
        {
            if(shopCarProductId[i] == "" || typeof(shopCarProductId[i]) == "undefined")
            {
                shopCarProductId.splice(i,1);
                i= i-1;

            }
        }
        console.log(shopCarProductId);
        $.ajax({
            url: '/shopCar/delete',
            dataType: 'json',
            type: 'post',
            contentType: "application/json; charset=utf-8",
            data:"["+shopCarProductId+"]",
            success: function (data) {
                alert(data.message);
                //刷新页面
                window.location.reload();
            }
        });
    }
}


function deleteProduct(obj){
        var shopCarProductId = $(obj).parent().find(".shopCarProductId").val();
        console.log(shopCarProductId);
        $.ajax({
            url: '/shopCar/delete',
            dataType: 'json',
            type: 'post',
            contentType: "application/json; charset=utf-8",
            data:"["+shopCarProductId+"]",
            success: function (data) {
                if($(obj).parents(".shopCar").find(".item-content").length == 1){
                    $(obj).parents(".shopCar").remove();
                }else {
                    $(obj).parents(".item-content").remove();
                }
                calculate();
            }
        });
}

function subOne(obj){
    var numberInput = $(obj).parent().find("input[type=number]");
    var number = $(numberInput).val();
    var min=$(numberInput).attr("min");
    if(parseInt(number)-parseInt(min) > 0){
        $(numberInput).val(parseInt(number)-1);
        changeNumber(numberInput);
    }else {
        alert("不能再小了");
    }

}

function addOne(obj){
    var numberInput = $(obj).parent().find("input[type=number]");
    var number = $(numberInput).val();
    var max=$(numberInput).attr("max");
    if(parseInt(max) > parseInt(number)){
        $(numberInput).val(parseInt(number)+1);
        changeNumber(numberInput);
    }else {
        alert("不能再大了");
    }
}

function changeNumber(obj) {
    var number = $(obj).val();
    var min=$(obj).attr("min");
    if(number >= min){
    }else {
        alert("不能再小了");
        return false;
    }
    var max=$(obj).attr("max");
    if(number >= max){
        alert("不能再大了");
        return false;
    }
    var shopCarProductId = $(obj).parents(".item-content").find(".shopCarProductId").val();
    var temp = {productNum:number,shopCarProductId:shopCarProductId};
    $.ajax({
        url: '/shopCar/updateNumber',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify(temp),
        success: function (data) {
            $(obj).parents(".item-content").find(".price-now").text(toDecimal2(data.message));
            $(obj).parents(".item-content").find(".productSum").text(toDecimal2(parseFloat(number)*parseFloat(data.message)));
            calculate();
        }
    });
}

//遮罩层修改价格 + -
function themeAdd() {
    var number = $(".theme-popover").find(".theme-productNumber").val();
    var max=99999;
    if(max > parseInt(number)){
        $(".theme-popover").find(".theme-productNumber").val(parseInt(number)+1);
    }else {
        alert("不能再大了");
    }
}

function themeSub() {
    var number = $(".theme-popover").find(".theme-productNumber").val();
    var min=1;
    if(parseInt(number)- min> 0){
        $(".theme-popover").find(".theme-productNumber").val(parseInt(number)-1);
    }else {
        alert("不能再小了");
    }

}

function themeChange(){
    var productId = $(".theme-popover").find(".theme-productId").val();
    var productSpec = $(".specList").find(".selected").html();
    var number = $(".theme-popover").find(".theme-productNumber").val();
    var temp = {productNumber:number,productId:productId,productSpec:productSpec};
    $.ajax({
        url: '/shopCar/selectPrice',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify(temp),
        success: function (data) {
            $(".theme-popover").find(".price-now").find("em").html(data.message);
        }
    });
}

function changeProductShopCar(){
    var productId =$(".theme-popover").find(".theme-productId").val();
    var shopCarId = $(".theme-popover").find(".theme-shopCarId").val();
    var shopCarProductId =$(".theme-popover").find(".theme-shopCarProductId").val();
    var productSpec = $(".specList").find(".selected").html();
    var productColor = $(".colorList").find(".selected").html();
    var number = $(".theme-popover").find(".theme-productNumber").val();
    var temp = {productId:productId,shopCarId:shopCarId,shopCarProductId:shopCarProductId,productNum:number,productSpec:productSpec,productColor:productColor};

    $.ajax({
        url: '/shopCar/update',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify(temp),
        success: function (data) {
            if(data.success){
                window.location.href = "/shopCar/getList";
            }
        }
    });
}