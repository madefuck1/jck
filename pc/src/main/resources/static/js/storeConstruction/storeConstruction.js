// 鼠标移动加载编辑
$(function () {
    initHtmlPage();
    $("#logo").hover(function () {
        $(".logo-mask").show();

    })
    $(".logo-mask").mouseout(function () {
        $(".logo-mask").hide();
    })
    $(".button1").hover(function () {
        $(".logo-mask").off("mouseout")
    });

    /*轮播图*/
    $(".side-left").hover(function () {
        $(".left-mask").show();

    })
    $(".left-mask").mouseout(function () {
        $(".left-mask").hide();
    })
    $(".button2").hover(function () {
        $(".left-mask").off("mouseout")
    });
    /*公司信息*/
    $(".right").hover(function () {
        $(".right-mask").show();

    })
    $(".right-mask").mouseout(function () {
        $(".right-mask").hide();
    })
    $(".button3").hover(function () {
        $(".right-mask").off("mouseout")
    });
    /*导航*/
    $(".product-title").hover(function () {
        $(".mask-productTitle").show();

    })
    $(".mask-productTitle").mouseout(function () {
        $(".mask-productTitle").hide();
    })
    $(".button4").hover(function () {
        $(".mask-productTitle").off("mouseout")
    });
    /*产品*/
    $(".product").hover(function () {
        $(".mask-product").show();

    })
    $(".mask-product").mouseout(function () {
        $(".mask-product").hide();
    })
    $(".button7").hover(function () {
        $(".mask-product").off("mouseout")
    });
    /*头部导航*/
    $("#nav2").hover(function () {
        $(".salenav-mask").show();

    })
    $(".salenav-mask").mouseout(function () {
        $(".salenav-mask").hide();
    })
    $(".button6").hover(function () {
        $(".salenav-mask").off("mouseout")
    });
    // 点击logo保存按钮
    $('#btn-logo-save').click(function () {
        logoSave();
    });
    // 点击logo取消按钮
    $('#btn-logo-cancel').click(function () {
        logoCancel();
    });
    // 点击编辑按钮初始化图片
    $('#btn-logo-mask').click(function () {
        btnLogoMask();
    })
    // 点击导航保存按钮
    $('#btn-nav-save').click(function () {
        btnNavSave();
    });
    // 点击导航取消按钮
    $('#btn-nav-cancel').click(function () {
        btnNavCancel();
    });
    // 检索轮播图产品
    $('#btn-search-product').on("click", function () {
        searchProduct();
    })

    // 点击轮播图保存按钮
    $('#btn-chart-save').click(function () {
        shopChartSave();
    });
    // 点击轮播图取消按钮
    $('#btn-chart-cancel').click(function () {
        shopChartCancel();
    });

    $('#addAssort').click(function () {
        addStoreA();
    });
    // 点击分类导航保存按钮
    $('#btn-storeA-save').click(function () {
        storeASave();
    });
    // 点击分类导航取消按钮
    $('#btn-storeA-cancel').click(function () {
        storeACancel();
    });


    $('#btn-mask-pro-save').click(function () {
        maskProSave()
    });
    $('#btn-mask-pro-cancel').click(function () {
        maskProCancel()
    });

    // 初始化分类1
    initStoreAssort();
    // 初始化分类2
    // initStoreAssort2();
    $('#btn-mask-product').click(function () {
        initStoreAssort2();
    })

    // 预览
    $('#btn-publish-preview').click(function () {
        window.open('/store/toStorePreview');
    })
    // 发布
    $('#btn-publish').click(function () {
        btnPublish();
    })

})

// 初始化分类
function initStoreAssort() {
    $.ajax({
        url: '/store/storeAssort/0',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            // 初始化轮播图产品分类
            var html = '<option value="" selected>所有分类</option>';
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].exclusiveAssortId + '">' + data[i].assortName + '</option>'
                }
            }
            $('#storeAssort').empty();
            $('#storeAssort').append(html);

            // 初始化店铺专属分类
            html = '';
            if (data.length != 0) {
                for (var i = 0; i < data.length; i++) {
                    html += '<div class="Lnav-r01" onclick="clickBox(this)">\n' +
                        '        <input type="checkbox" name="checkbox">\n' +
                        '        <p>' + data[i].assortName + '</p>\n' +
                        '        <input hidden name="exclusiveAssortId" value="' + data[i].exclusiveAssortId + '">\n' +
                        '    </div>';
                }
            }
            $('#store-assort-tab1').empty();
            $('#store-assort-tab1').append(html);
        }
    });
}

// 初始化分类
function initStoreAssort2() {
    $.ajax({
        url: '/store/storeAssort/1',
        dataType: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        success: function (data) {

            // 初始产品分类
            var html = '';
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].exclusiveAssortId + '">' + data[i].assortName + '</option>'
                }
            }
            $('#mask-pro-select1').empty();
            $('#mask-pro-select1').append(html);

        }
    });
}

//重新激活鼠标移开动作
function initAgain() {
    $(".logo-mask").mouseout(function () {
        $(".logo-mask").hide();
    })
    $(".mask-productTitle").mouseout(function () {
        $(".mask-productTitle").hide();
    })
    $(".salenav-mask").mouseout(function () {
        $(".salenav-mask").hide();
    })
    $(".left-mask").mouseout(function () {
        $(".left-mask").hide();
    })
    $(".right-mask").mouseout(function () {
        $(".right-mask").hide();
    })
    $(".mask-product").mouseout(function () {
        $(".mask-product").hide();
    })
}

// /*产品分类点击切换*/
// $(".Lnav-r01").click(function () {
//     $(this).addClass("cur").siblings().removeClass("cur")
// })

// 定义一个logo图片
function changeLogoPic(obj) {

    var reads = new FileReader();
    var logoFile = obj.files[0];

    var img = URL.createObjectURL(logoFile);
    var image = new Image();
    image.src = img;
    image.onload = function (ev) {
        if (image.width != 1920 && image.height != 120) {
            $(obj).val("");
            alert("图片大小不符合标准");
        } else {
            reads.readAsDataURL(logoFile);
            reads.onload = function (e) {
                $('#masklogo').html('<img width="1042px;" src="' + this.result + '">');
            };
        }
    }
}

// logo 保存
function logoSave() {
    var formData = new FormData();
    formData.append('file', $('#logoPic')[0].files[0]);
    $.ajax({
        url: '/store/logoPicUpload',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false
    }).done(function (res) {
        if (res.success) {
            var attr = $('#masklogo').find('img').attr("src");
            $('#logo').html('<img width="1920px;" src=' + attr + '>');
            logoCancel();
        } else {
            alert(res.message);
        }
    });
}

// logo取消
function logoCancel() {
    $('#doc-modal-1').modal({
        closable: false
    });
    initAgain();
    $(".logo-mask").hide();
}

function btnLogoMask() {
    $('#masklogo').html('');
}

// 首页导航背景颜色保存
function btnNavSave() {
    var background = $('#bgColor').val();
    $.ajax({
        url: '/store/upd/' + background,
        dataType: 'json',
        type: 'get',
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            if (res.success) {
                $('#nav2').css('background', background);
                btnNavCancel();
            } else {
                alert(res.message)
            }
        }
    });
}

function btnNavCancel() {
    $('#doc-modal-5').modal({
        closable: false
    });
    initAgain();
    $("#salenav-mask").hide();
}

// ---轮播图----------------------------------------------------
// 轮播图编辑页面产品列表选中背景颜色设置，并改变内容
// 选中改变背景颜色
function changeBackgroundColor(obj) {
    $(obj).parent().find('.pro1').css('background-color', "#fff");
    $(obj).css('background-color', "#23ccfe");
}

// 双击修改内容
function dblclickP(obj) {
    $(obj).parent().find('.pro1').css('background-color', "#fff");
    $(obj).css('background-color', "#23ccfe");
    setTimeout(function () {
        $('#left-mask-tabs').hide();
        // 获取列表产品名
        var productName = $(obj).find('.prod').find('p').html();
        // 获取列表产品id
        var porductId = $(obj).find('input').val();
        // 轮播图跳转地址
        var chartLink = "javascript:window.open('/product/toDetail?productId=" + porductId + "','_blank')";
        var html = "";
        $('#mask-con-2').find('.picLink').removeClass('up-pic');
        html += '<input hidden name="chartLink" value="' + chartLink + '">';
        html += '<p class="upload-picload-p" onmouseenter="showAllFileName(this,1);" onmouseout="showAllFileName(this,2);">' + productName + '</p>';
        html += '<a href="javascript: void(0)" onclick="selectProduct(this);">选择</a>';
        $('#mask-con-2').find('.picLink').html(html);
    }, 5);
}

// 轮播图编辑页面 点击选择链接显示产品列表
function selectProduct(obj) {
    $('#tab1-pro-down').empty();
    $('#mask-con-2').find('.proload').each(function () {
        $(this).find('a').css('color', '#999999');
    })
    $(obj).css('color', 'red')
    $('#left-mask-tabs').show();
    $('#mask-con-2').find('.proload').removeClass('picLink');
    $(obj).parent().addClass("picLink");
    searchProduct();
}

// 点击上传图片 logo
function uploadPic(obj) {
    $(obj).parents('.upload-chart').find('input').click();
    $('#mask-con-2').find('.proload').removeClass('upPic');
    $(obj).parent().addClass("upPic");
}

// 选中图片 后更新内容 logo
function changeValue(obj) {
    var filePath = $(obj).val();
    var fileName = filePath.substring(filePath.lastIndexOf('\\') + 1)
    var html = ""
    $(obj).parents('.upload-chart').find('.upPic').removeClass('up-pic');
    html += '<p class="upload-picload-p" onmouseenter="showAllFileName(this,1);" onmouseout="showAllFileName(this,2);">' + fileName + '</p>';
    html += '<a class="aChangeColor" href="javascript: void(0)" onclick="uploadPic(this);">替换</a>';
    $(obj).parents('.upload-chart').find('.upPic').html(html);
}

// 鼠标移动到文件名上显示全部内容
function showAllFileName(obj, type) {
    if (type == 1) {
        $(obj).css("width", "190px");
        $(obj).parent().find('a').hide();
    } else {
        $(obj).css("width", "100px");
        $(obj).parent().find('a').show();
    }
}

function addShopChart(obj) {
    var length = $('#mask-con-2').find('.upload-chart').length;
    if (length < 4) {
        var html = '<div class="upload upload-chart">\n' +
            '           <div class="am-form-group am-form-file" hidden>\n' +
            '               <input name="fileName" type="file" multiple onchange="changeValue(this)">\n' +
            '           </div>\n' +
            '           <div class="picload up-pic">\n' +
            '               <a href="javascript: void(0)" onclick="uploadPic(this);">上传图片</a>\n' +
            '           </div>\n' +
            '           <div class="proload up-pic">\n' +
            '               <a href="javascript: void(0)" onclick="selectProduct(this);">选择跳转链接</a>\n' +
            '           </div>\n' +
            '           <div class="icon">\n' +
            '               <span onclick="upClick(this);" class="up" style="cursor: pointer"></span>\n' +
            '               <span onclick="downClick(this);" class="down" style="cursor: pointer"></span>\n' +
            '               <span onclick="delClick(this);" class="del" style="cursor: pointer"></span>\n' +
            '           </div>\n' +
            '       </div>';
        $(obj).parent().before(html);
    } else {
        alert("图片最多只能选择上传4张，请酌情添加！")
    }
}

// 轮播图上移
function upClick(obj) {

    if ($(obj).parents('.upload-chart').prev().attr('class') == 'upload upload-chart') {
        $(obj).parents('.upload-chart').after($(obj).parents('.upload-chart').prev());
    }


}

// 轮播图下移
function downClick(obj) {
    if ($(obj).parents('.upload-chart').next().attr('class') != "upload") {
        $(obj).parents('.upload-chart').next().after($(obj).parents('.upload-chart'));
    }
}

// 轮播图删除
function delClick(obj) {
    $(obj).parents('.upload-chart').remove();
}

// 初始化轮播图产品列表
function searchProduct() {
    var param = {};
    var storeAssort = $('#storeAssort').val();
    var productName = $('#productName').val();
    if (storeAssort != null && storeAssort != undefined && storeAssort != "") {
        param.storeAssort = storeAssort;
    }
    if (productName != null && productName != undefined && productName != "") {
        param.productName = productName;
    }

    $.ajax({
        url: '/store/searchProduct',
        dataType: 'json',
        type: 'POST',
        data: JSON.stringify(param),
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            var html = '';
            if (res.length > 0) {
                for (var i = 0; i < res.length; i++) {
                    html += '<div class="pro1" onclick="changeBackgroundColor(this);" ondblclick="dblclickP(this);" >\n' +
                        '        <input hidden name="productId" value="' + res[i].productDto.productId + '">\n' +
                        '        <div class="prod am-fl">\n' +
                        '            <span class="pro-cloum1"></span>\n' +
                        '            <p>' + res[i].productDto.productName + '</p>\n' +
                        '        </div>\n' +
                        '        <p class="am-fl" style="color: #333;margin-left: 30px;">￥' + Number(res[i].productDto.productStatisticsDto.productPrice).toFixed(2) + '</p>\n' +
                        '    </div>';
                }
            } else {
                html += '<div style="text-align: center;padding-top: 20px">还没有上架的产品</div>';
            }
            $('#tab1-pro-down').empty();
            $('#tab1-pro-down').append(html);
        }
    });
}

// 轮播图 保存
function shopChartSave() {
    var formData = new FormData();
    $('#mask-con-2').find('.upload-chart').each(function () {
        if ($(this).find('[name=fileName]')[0] != undefined) {
            var file = $(this).find('[name=fileName]')[0].files[0];
            formData.append('file', file);
            var chartLink = $(this).find('[name=chartLink]').val();
            formData.append("chartLink", chartLink);
        }
    })

    $.ajax({
        url: '/store/saveShopChart',
        type: 'post',
        cache: false,
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
    }).done(function (res) {
        if (res.success) {
            var list = res.list;
            var html = '';
            for (var i = 0; i < list.length; i++) {
                html += '<li>\n' +
                    '      <img src="' + list[i].mapURL + '" alt="">\n' +
                    '  </li>';
            }
            $('#shop-chart-map').empty();
            $('#shop-chart-map').append(html);
            shopChartCancel();
        } else {
            alert("图片上传失败！")
        }
    });
}

// 轮播图取消
function shopChartCancel() {
    $('#doc-modal-2').modal({
        closable: false
    });
    initAgain();
    $(".left-mask").hide();
}

// 点击分类按钮保存按钮
function storeASave() {
    var html = '';
    var assortIds = '';
    $('#store-assort-tab0').find('.Lnav-r01').each(function () {
        assortIds += ',' + $(this).find('[name=exclusiveAssortId]').val();
        html += '<a href="#">' + $(this).find('p').html() + ' <input hidden name="exclusiveAssortId" value="' + $(this).find('[name=exclusiveAssortId]').val() + '"></a>';
    })

    html += '<div class="product-search" id="product-search">\n' +
        '        <input type="text" placeholder="店内搜索">\n' +
        '        <button type="button" class="am-btn am-btn-warning">搜索</button>\n' +
        '    </div>';

    $.ajax({
        url: '/store/storeASave/' + assortIds,
        dataType: 'json',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            if (res.success) {
                $('#product-title').empty();
                $('#product-title').append(html);
                storeACancel();
            }
        }
    });
}

// 点击分类导航取消按钮
function storeACancel() {
    $('#doc-modal-4').modal({
        closable: false
    });
    initAgain();
    $(".mask-productTitle").hide();
}

// 添加分类
function addStoreA() {
    var html = '';
    $('#store-assort-tab1').find('.Lnav-r01').each(function () {
        if ($(this).find('[name=checkbox]').is(':checked')) {
            html += '<div class="Lnav-r01">\n' +
                '        <p>' + $(this).find('p').html() + '</p>\n' +
                '        <input hidden name="exclusiveAssortId" value="' + $(this).find('[name=exclusiveAssortId]').val() + '">\n' +
                '        <div class="icon">\n' +
                '            <span onclick="assortUpClick(this)" style="cursor: pointer" class="up"></span>\n' +
                '            <span onclick="assortDownClick(this)" style="cursor: pointer" class="down"></span>\n' +
                '            <span onclick="assortDelClick(this)" style="cursor: pointer" class="del"></span>\n' +
                '        </div>\n' +
                '    </div>';
        }
    })
    $('#store-assort-tab0').empty();
    $('#store-assort-tab0').append(html);

}

// 专属店铺分类 上移
function assortUpClick(obj) {
    if ($(obj).parents('.Lnav-r01').prev().length != 0) {
        $(obj).parents('.Lnav-r01').after($(obj).parents('.Lnav-r01').prev());
    } else {
        alert("已经在最上面了，不能再往上移动了")
    }
}

// 专属店铺分类 下移
function assortDownClick(obj) {
    if ($(obj).parents('.Lnav-r01').next().length != 0) {
        $(obj).parents('.Lnav-r01').next().after($(obj).parents('.Lnav-r01'));
    } else {
        alert("已经在最下面了，不能再往下移动了")
    }
}

// 专属店铺分类 删除
function assortDelClick(obj) {
    $(obj).parents('.Lnav-r01').remove();
}

// 点击分类选中
function clickBox(obj) {
    $(obj).find('[name=checkbox]').click();
}

// 产品排序按钮保存按钮
function maskProSave() {
    var param = {
        exclusiveAssortId: $('#mask-pro-select1').val(),
        sortName: $('#mask-pro-select2').val(),
    }

    $.ajax({
        url: '/store/saveProductSort',
        dataType: 'json',
        type: 'POST',
        data: JSON.stringify(param),
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            if (res.success) {
                alert("保存成功！")
            }
        }
    });
}

// 产品排序取消按钮
function maskProCancel() {
    $('#doc-modal-10').modal({
        closable: false
    });
    initAgain();
    $(".mask-product").hide();
}

// 初始化页面
function initHtmlPage() {

    $.ajax({
        url: '/store/getStoreConstructionInfo/',
        dataType: 'json',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            var html = '';
            //------------- 初始化logo-------
            var storeLogoUrl = res.storeConstructionDto.storeLogoUrl;
            if (storeLogoUrl != '') {
                $('#logo').html('<img src="' + storeLogoUrl + '" alt="">');
            } else {
                // todo
            }
            //------------- 初始首页/公司概况/视频中心化背景颜色----------------
            var storeNavColor = res.storeConstructionDto.storeNavColor;
            if (storeNavColor != null && storeNavColor != '') {
                $('#nav2').css('background', storeNavColor);
            } else {
                // todo
            }
            //------------- 初始化轮播图 -------------------
            var storeCurouselMapDtoList = res.storeConstructionDto.storeCurouselMapDtoList;
            if (storeCurouselMapDtoList.length > 0) {
                for (var i = 0; i < storeCurouselMapDtoList.length; i++) {
                    $('#side-left').flexslider('addSlide', '<li><img src="' + storeCurouselMapDtoList[i].mapURL + '" alt=""></li>');
                    if (i == 0) {
                        $('#side-left').flexslider('removeSlide', 0);
                    }
                }
            } else {
                // todo
            }
            //------------- 初始化公司信息-------------------
            $('#companyName').html(res.companyDto.compName);
            $('#linkPhone').html(res.companyDto.compPhone);
            $('#mainProducts').html(res.shopDto.mainProducts);
            $('#companyAddress').html(res.companyDto.compAddress);
            $('#companyInfo').html(res.companyDto.companyInfo);

            //------------- 初始化分类-------------
            html = '';
            var storeExclusiveAssortDtoList = res.storeConstructionDto.storeExclusiveAssortDtoList;
            if (storeExclusiveAssortDtoList.length > 0) {
                for (var i = 0; i < storeExclusiveAssortDtoList.length; i++) {
                    html += '<a href="#">' + storeExclusiveAssortDtoList[i].assortName + ' <input hidden name="exclusiveAssortId" value="' + storeExclusiveAssortDtoList[i].exclusiveAssortId + '"></a>';

                }
                html += '<div class="product-search" id="product-search">\n' +
                    '        <input type="text" placeholder="店内搜索">\n' +
                    '        <button type="button" class="am-btn am-btn-warning">搜索</button>\n' +
                    '    </div>';

                $('#product-title').empty();
                $('#product-title').append(html);
            } else {
                // todo
            }
            //------------- 初始化产品-------------
            //装修中，不展示产品。默认在浏览中展示产品
        }
    });

}

function btnPublish() {

    $.ajax({
        url: '/store/publish/',
        dataType: 'json',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if (data.success) {
                alert("发布成功！")
            } else {
                alert("发布失败，请重新发布！")
            }
        }
    });
}


















