var paramNumber = 1;  //属性productJson 区分是否需要换行
var colorNumber = 0;  //productColorDtoList 数量
var priceNumber = 1;  //productSpecDtoList 数量
var specNumber = 1;

var paramHtml = '<div class="paramInput"><div class="attr-row1 am-u-sm-2">\n' +
    '<span class="am-form-horizontal">\n' +
    '<input type="text" class="paramKey" placeholder="属性名">\n' +
    '</span>\n' +
    '</div>\n' +
    '<div class="attr-row2 am-u-sm-4">\n' +
    '<span class="am-form-horizontal">\n' +
    '<input type="email" class="paramValue" placeholder="请输入">\n' +
    '</span></div>\n';

var colorHtml = '<span class="base-cloum1">\n' +
    '<span class="am-u-sm-2">\n' +
    '    <span>子规格：</span>\n' +
    '</span>\n' +
    '<span class="am-u-sm-10 am-form">\n' +
    '     <input type="text" class="" name="productColorDtoList[0].productColor"  placeholder="请输入子规格">\n' +
    '</span>\n' +
    '</span>';

var priceHtml = '<div class="attr1-cloum2">\n' +
    '    <div class="am-u-sm-6">\n' +
    '        <input type="number"  class="minNumber" required onchange="writeNumber(this)"  name="productSpecDtoList[0].minNumber">\n' +
    '        <input type="hidden"  class="maxNumber"  name="productSpecDtoList[0].maxNumber">\n' +
    '        <input type="hidden"  class="specName"  name="productSpecDtoList[0].specName">\n' +
    '    </div>\n' +
    '    <div class="am-u-sm-6">\n' +
    '        <input type="number"class="specNumber" required onchange="writeNumber(this)"  name="productSpecDtoList[0].specNumber">\n' +
    '        <span class="attr1-pic"></span>\n' +
    '    </div>\n' +
    '</div>';

var priceShowHtml = '<div class="attr1-cloum2 attr1-right">\n' +
    '    <div class="am-u-sm-6">\n' +
    '        <p class="numberSection"></p>\n' +
    '    </div>\n' +
    '    <div class="am-u-sm-6">\n' +
    '        <p>￥<span class="numberPrice" style="color:#F58022 "></span>/米</p>\n' +
    '    </div>\n' +
    '</div>';

function addParamHtml() {
    var html = "";
    if (paramNumber % 2 == 1) {
        html += '<div class="attr-cloum1 attr-cloum4">';
        html += paramHtml;
        html += '</div>';
        $(".attr").append(html);
        $(".base").css("min-height", parseInt($(".base").height() + 70) + "px");
        $(".attr").css("min-height", parseInt($(".attr").height() + 70) + "px");
        paramNumber++;
    } else {
        html += paramHtml;
        $(".attr").find(".attr-cloum1").last().append(html);
        paramNumber++;
    }
}

function addColorHtml(obj) {
    colorNumber++;
    var html = colorHtml;
    html = html.replace('productColorDtoList[0]', 'productColorDtoList[' + colorNumber + ']');
    $(obj).parent().find(".colorList").append(html);

}

function addPriceHtml(obj) {
    var html = priceHtml;
    html = html.replace("productSpecDtoList[0]", 'productSpecDtoList[' + priceNumber + ']')
        .replace("productSpecDtoList[0]", 'productSpecDtoList[' + priceNumber + ']')
        .replace("productSpecDtoList[0]", 'productSpecDtoList[' + priceNumber + ']')
        .replace("productSpecDtoList[0]", 'productSpecDtoList[' + priceNumber + ']');
    priceNumber++;
    $(obj).parent().find(".priceInput").append(html);
    $(obj).parent().find(".priceShow").append(priceShowHtml);
}


function writeNumber(obj) {
    $(obj).parents(".attr1-cloum2").prev().find(".maxNumber").val($(obj).parents(".attr1-cloum2").find(".minNumber").val());
    showPriceSection($(obj).parents(".attr1"));
}


function showPriceSection(obj) {
    var minNumberList = [];
    var priceList = [];
    var i = 0, total;
    $(obj).find(".priceInput").find(".minNumber").each(function () {
        minNumberList[i] = $(this).val();
        i++;
    })
    total = i;
    i = 0;
    $(obj).find(".priceInput").find(".specNumber").each(function () {
        priceList[i] = $(this).val();
        i++;
    })
    i = 0;
    $(obj).find(".priceShow").find(".numberSection").each(function () {
        if (i < total - 1) {
            $(this).html(minNumberList[i] + "--" + minNumberList[i + 1]);
        } else {
            $(this).html(">" + minNumberList[i]);
        }
        i++;
    })
    i = 0;
    $(obj).find(".priceShow").find(".numberPrice").each(function () {
        $(this).html(priceList[i]);
        i++;
    })
}

function addSpecHtml() {
    var html = $("#spec0").clone();
    html.attr("id", "spec" + specNumber);
    specNumber++;
    html.find("h4").html("产品规格" + specNumber);
    html.find(".base-cloum1").first().remove();
    html.find("input[name=productUnit]").attr("name", "");
    html.find(".productColor").attr("name", "");
    html.find(".specName").val("");

    var i = 0;
    html.find(".priceInput").find(".attr1-cloum2").each(function () {
        if (i > 0) {
            $(this).remove();
        }
        i++;
    });
    i = 0;
    html.find(".priceShow").find(".attr1-cloum2").each(function () {
        if (i > 0) {
            $(this).remove();
        } else {
            $(this).find(".numberSection").html("");
            $(this).find(".numberPrice").html("");
        }
        i++;
    });
    html.find(".attr1-cloum2 .minNumber").attr("name", "productSpecDtoList[" + priceNumber + "].minNumber");
    html.find(".attr1-cloum2 .maxNumber").attr("name", "productSpecDtoList[" + priceNumber + "].maxNumber");
    html.find(".attr1-cloum2 .specNumber").attr("name", "productSpecDtoList[" + priceNumber + "].specNumber");
    html.find(".attr1-cloum2 .specName").attr("name", "productSpecDtoList[" + priceNumber + "].specName");
    priceNumber++;

    $(".specList").append(html);
}

$(".Master-pic .mpic-1 img").bind("click", function () {
    $(this).parent().find("input").click();
})

//图片预览
$(".Master-pic .mpic-1 input").bind("change", function () {
    //新增图片
    var obj = this;
    var reads = new FileReader();
    var file = this.files[0];

    var size = file.size;
    if (checkFileSize(size)) {
        var img = URL.createObjectURL(file);
        var image = new Image();
        image.src = img;
        image.onload = function (ev) {
            if (image.width != 400) {
                $(obj).parent().find("input").val("");
                alert("图片大小不符合标准");
            } else {
                reads.readAsDataURL(file);
                reads.onload = function (e) {
                    $(obj).parent().find("img").attr("src", this.result);
                };
                $(this).parent().append("<p onclick='deleteImage(this)'>删除</p>")
            }
        }
    } else {
        alert("文件大小超过限制");
    }


})

function deleteImage(obj) {
    $(obj).parent().find("img").attr("src", "/static/images/sale/01.png");
    //清空file
    $(obj).parent().find("input").val("");
    $(obj).parent().find("p").remove();
}

//产品详情
$(".imageDetail").bind("click", function () {
    $(this).parent().find("input").last().click();
})

function imageDetail(obj) {
    var reads = new FileReader();
    var file = obj.files[0];
    var size = file.size;
    if (checkFileSize(size)) {
        var img = URL.createObjectURL(file);
        var image = new Image();
        image.src = img;
        image.onload = function (ev) {
            if (image.width != 750) {
                $(obj).val("");
                alert("图片大小不符合标准");
            } else {
                reads.readAsDataURL(file);
                reads.onload = function (e) {
                    $(obj).parent().append('<img src="' + this.result + '">');
                };
                $(obj).parent().find("input[type=file]").removeAttr("onchange");
                $(obj).parent().append('<input type="file" onchange="imageDetail(this)" style="display: none">');
            }
        }
    } else {
        alert("文件大小超出限制");
    }
}

function checkForm() {
    var message = '';
    if ($("input[name=productName]").val() == '') {
        return "产品名称未填写";
    }
    $(".attr").find("input").each(function () {
        if ($(this).val() == '') {
            return "产品属性未填写完整";
        }
    })
    $(".priceInput .minNumber").each(function () {
        var min = $(this).val();
        var max = $(this).parent().find(".maxNumber").val();
        if (max != '') {
            if ((parseInt(max) - parseInt(min)) < 0) {
                message = "价格区间填写有误";
            }
        }
    })
    if (message != '') {
        return message;
    }
    $(".specNumber").each(function () {
        if ($(this).val() == '') {
            message = "价格未填写";
        }
    })
    if (message != '') {
        return message;
    }
    if ($(".specName").val() == '') {
        return "规格名未填写";
    }
    return null;
}

function createForm() {
    $('#submit-product').prop("disabled", true)
    var reason = checkForm();
    if (reason != null) {
        alert(reason);
    } else {
        var formData = new FormData();
        var html = "";
        //productJson
        var productJson = "";
        $(".paramKey").each(function () {
            productJson += $(this).val() + ":" + $(this).parents(".paramInput").find(".paramValue").val() + ";";
        })
        html += '<input type="hidden" name="productJson" value="' + productJson + '">';

        $(".base1").each(function () {
            var specName = $(this).find(".specName").val();
            $(this).find(".priceInput").find(".attr1-cloum2").each(function () {
                $(this).find(".specName").val(specName);
            })
        })
        $(".Master-pic .mpic-1 input").each(function () {
            if ($(this)[0].files[0] != undefined) {
                formData.append("files", $(this)[0].files[0]);
            }
        })
        $(".Master-pic2 input[type=file]").each(function () {
            if ($(this)[0].files[0] != undefined) {
                formData.append("files2", $(this)[0].files[0]);
            }
        })

        $("#createForm").append(html);


        var form = $("#createForm");
        var options = {
            url: '/product/create',
            type: 'post',
            success: function (data) {
                if (data.success) {
                    formData.append("productId", data.message);
                    $.ajax({
                        url: '/product/createSecond  ',
                        type: 'post',
                        cache: false,
                        data: formData,
                        dataType: "json",
                        processData: false,
                        contentType: false,
                        beforeSend: function () {
                            loading("数据提交中，请稍后......");  // 数据加载成功之前，使用loading组件
                        },
                        success: function (data) {
                            alert("新增产品成功");
                            window.location.href = "/sellerCenter/toProductList";
                            window.location.href="/sellerCenter/toProductList";
                        },error: function (data) {
                            alert("网络请求失败，请重试！");
                        }
                    });
                }
            }
        };
        form.ajaxSubmit(options);
    }
}
