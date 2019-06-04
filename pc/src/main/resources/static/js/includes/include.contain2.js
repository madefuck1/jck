$(document).ready(function () {
    var obj = $(".list_down .list_left .meu");
    $(obj).each(function () {
        $(this).mouseenter(function () {
            $(this).css("background", "rgba(245,128,34,1)");
            $(this).find(".span1").css("color", "#fff");
            var assortId = $(this).find("input[type=hidden]").val();
            var assortName = $(this).find((".span1")).html();
            var html = '<p class="p1">' + assortName + '</p>';
            $.ajax({
                url: '/pc/commonPullDown/getAssortByParentId/' + assortId,
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $(".list_con").html("");
                    if (data) {
                        for (var i = 0; i < data.length; i++) {
                            html += '<div class="list_oncloum1">';
                            html += '<span class="title" style="cursor: pointer" onclick="clickAssortName(\'B' + data[i].assortId + '\',\'' + data[i].assortName + '\')">' + data[i].assortName + '</span>';
                            html += '<span class="con">';
                            for (var j = 0; j < data[i].children.length; j++) {
                                html += '<span style="cursor: pointer" onclick="clickAssortName(\'C' + data[i].children[j].assortId + '\',\'' + data[i].children[j].assortName + '\')">' + data[i].children[j].assortName + '</span>';
                            }
                            html += '</span></div>';
                        }
                        $(".list_con").append(html);

                    } else {
                        $(".list_con").append(html);
                    }

                    $(".list_con").find('.title').hover(function () {
                        $(this).css("color", "#00F");
                    }, function () {
                        $(this).css("color", "");

                    })
                    $(".list_con").find('.con').find('span').hover(function () {
                        $(this).css("color", "#00F");
                    }, function () {
                        $(this).css("color", "");

                    })
                }
            });
            $(".list_right").show();
        }).mouseout(function () {
            $(this).css("background", "#fff");
            $(this).find(".span1").css("color", "#333");
        });

        $(".list_down").mouseleave(function () {
            setTimeout(function () {
                $(".list_right").hide();
            }, 1);
        })

        $(".list_con").mouseleave(function () {
            setTimeout(function () {
                $(".list_right").hide();
            }, 1);
        })

    })
});

function clickAssortName(k, v) {
    console.log(k + ":" + v)
    window.location.href = '/product/search?assortId=' + k + '&assortName=' + v;
}


