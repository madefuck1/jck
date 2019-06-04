$(document).ready(function () {

    var hh = document.documentElement.clientHeight;
    var ls = document.documentElement.clientWidth;
    if (ls < 640) {
        $(".select dt").click(function () {
            if ($(this).next("div").css("display") == 'none') {
                $(".theme-popover-mask").height(hh);
                $(".theme-popover").css("position", "fixed");
                $(this).next("div").slideToggle("slow");
                $(".select div").not($(this).next()).hide();
            } else {
                $(".theme-popover-mask").height(0);
                $(".theme-popover").css("position", "static");
                $(this).next("div").slideUp("slow");
            }
        });


        $(".eliminateCriteria").on("click", function () {
            $(".dd-conent").hide();
        });

        $(".select dd").on("click", function () {
            $(".theme-popover-mask").height(0);
            $(".theme-popover").css("position", "static");
            $(".dd-conent").hide();
        });
        $(".theme-popover-mask").on("click", function () {
            $(".theme-popover-mask").height(0);
            $(".theme-popover").css("position", "static");
            $(".dd-conent").hide();
        });

    }


    $("span.love").click(function () {
        $(this).toggleClass("active");
    });


    $("#select1 dd").click( function () {
        $(this).addClass("selected").siblings().removeClass("selected");
        if ($(this).hasClass("select-all")) {
            $("#selectA").remove();
            $("#selectB").remove();
            $("#selectC").remove();
            $("#select2").html("");
            $("#select3").html("");
            searchAssortWithSort(0);
        } else {
            var copyThisA = $(this).clone();
            if ($("#selectA").length > 0) {
                $("#selectA").remove();
                copyThisA.attr("onclick", "selectA()");
                copyThisA.attr("id", "selectA");
                $(".select-result dl").append(copyThisA);
                getAssort(this, "#select2");
                $("#selectB").remove();
                $("#selectC").remove();
                $("#select3").html("");
            } else {
                copyThisA.attr("onclick", "selectA()");
                copyThisA.attr("id", "selectA");
                $(".select-result dl").append(copyThisA);
                getAssort(this, "#select2");
                $("#selectB").remove();
                $("#selectC").remove();
                $("#select3").html("");
            }
            setTimeout(function () {
                $("[name=select2-list]").css('display', 'block');
                searchAssortWithSort(0);
            }, 1)
        }
    });

    function getAssort(value, dlId) {
        var val = $(value).find('input').val();
        $.get("/pc/commonPullDown/getAssortAByParentId/" + val, function (result) {
            var html = "";
            if (result.length != 0) {

                html += '<div class="dd-conent">';
                html += '<dd class="select-all selected"><a href="#">全部</a></dd>';
                for (var i = 0; i < result.length; i++) {
                    html += '<dd><a href="#">' + result[i].assortName + '<input hidden ' +
                        'value="' + result[i].assortId + '"></a></dd>';
                }
                html += '</div>';
            }
            $(dlId).text("");
            $(dlId).append(html);
            switch (dlId) {
                case "#select2":
                    $("#select2").find("dd").click(function () {
                        $(this).addClass("selected").siblings().removeClass("selected");
                        if ($(this).hasClass("select-all")) {
                            $("#selectB").remove();
                            $("#selectC").remove();
                            $("#select3").html("");
                            searchAssortWithSort(0);
                        } else {
                            var copyThisB = $(this).clone();
                            if ($("#selectB").length > 0) {
                                $("#selectB").remove();
                                copyThisB.attr("onclick", "selectB()");
                                copyThisB.attr("id", "selectB");
                                $(".select-result dl").append(copyThisB);
                                getAssort(this, "#select3");
                                $("#selectC").remove();
                            } else {
                                copyThisB.attr("onclick", "selectB()");
                                copyThisB.attr("id", "selectB");
                                $(".select-result dl").append(copyThisB);
                                getAssort(this, "#select3");
                                $("#selectC").remove();
                            }
                            setTimeout(function () {
                                $("[name=select3-list]").css('display', 'block');
                                searchAssortWithSort(0);

                            }, 1)
                        }
                    });
                    break;
                case "#select3":
                    $("#select3 dd").click(function () {
                        $(this).addClass("selected").siblings().removeClass("selected");
                        if ($(this).hasClass("select-all")) {
                            $("#selectC").remove();
                            searchAssortWithSort(0);
                        } else {
                            var copyThisC = $(this).clone();
                            if ($("#selectC").length > 0) {
                                $("#selectC").remove();
                                copyThisC.attr("onclick", "selectC()");
                                copyThisC.attr("id", "selectC");
                                $(".select-result dl").append(copyThisC);
                            } else {
                                copyThisC.attr("onclick", "selectC()");
                                copyThisC.attr("id", "selectC");
                                $(".select-result dl").append(copyThisC);
                            }
                            setTimeout(function () {
                                searchAssortWithSort(0);

                            }, 1)
                        }
                    });
                    break;
            }
        });
    }

    $(".select dd").click(function () {
        if ($(".select-result dd").length > 1) {
            $(".select-no").hide();
            $(".eliminateCriteria").show();
            $(".select-result").show();
        } else {
            $(".select-no").show();
            $(".select-result").hide();

        }
    });

    $(".eliminateCriteria").on("click", function () {
        $("#selectA").remove();
        $("#selectB").remove();
        $("#selectC").remove();
        $("#select2").html("");
        $("#select3").html("");
        $(".select-all").addClass("selected").siblings().removeClass("selected");
        $(".eliminateCriteria").hide();
        $(".select-no").show();
        $(".select-result").hide();
        setTimeout(function () {
            searchAssortWithSort(0);
        }, 1)
    });


});