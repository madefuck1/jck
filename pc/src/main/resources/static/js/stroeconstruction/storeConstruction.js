// 鼠标移动加载编辑
$(function () {
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
    $("#nav").hover(function () {
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

})

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

// 轮播图编辑页面产品列表选中背景颜色设置
function changeBackgroundColor(obj) {
    $(obj).parent().find('.pro1').css('background-color', "#fff");
    $(obj).css('background-color', "#23ccfe");
    setTimeout(function () {
        $('#left-mask-tabs').hide();
    }, 1000);
}

// 轮播图编辑页面 点击选择链接显示产品列表
function selectProduct(obj) {
    $('#left-mask-tabs').show();
}

// 点击上传图片
function uploadPic(obj) {
    $(obj).parents('.upload').find('input').click();
}

// 选中图片 后更新内容
function changeValue(obj) {
    var filePath = $(obj).val();
    var fileName = filePath.substring(filePath.lastIndexOf('\\') + 1)
    var html = ""
    $(obj).parents('.upload').find('.picload').removeClass('up-pic');
    html += '<p class="upload-picload-p" onmouseenter="showAllFileName(this,1);" onmouseout="showAllFileName(this,2);">' + fileName + '</p>';
    html += '<a href="javascript: void(0)" onclick="uploadPic(this);">替换</a>';
    $(obj).parents('.upload').find('.picload').html(html);
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

var param = {
    file: $('#logoPic')[0],
    background: $('#bgColor').val()
}

function logoSave() {
    var formData = new FormData();
    formData.append('file',$('#logoPic')[0].files[0]);
    $.ajax({
        url: '/store/logoPicUpload',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false
    }).done(function (res) {
        var attr = $('#masklogo').find('img').attr("src");
        $('#logo').html('<img width="1920px;" src=' + attr + '>');
        logoCancel();
    }).fail(function (res) {

    });
}

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

function btnNavSave() {
    var background = $('#bgColor').val();
    $('#nav').css('background', background);
    btnNavCancel();
}

function btnNavCancel() {
    $('#doc-modal-5').modal({
        closable: false
    });
    initAgain();
    $("#salenav-mask").hide();
}