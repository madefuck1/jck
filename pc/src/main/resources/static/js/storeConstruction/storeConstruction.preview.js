$(function () {
   initHtmlPage();

})

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
               $('#side-left').flexslider('addSlide', '<li style="cursor: pointer" onclick="' + storeCurouselMapDtoList[i].storeCurouselMapLink + '"><img src="' + storeCurouselMapDtoList[i].mapURL + '" alt=""></li>');
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

         //------------- 初始化公司概况-------------------
         $('#company_name').html(res.companyDto.compName);
         $('#company_address').html(res.companyDto.compAddress);
         $('#company_corporate').html(res.companyDto.compCorporate);
         $('#company_type').html(res.companyDto.compTypeString);
         $('#business_scope').html(res.shopDto.businessScope);
         $('#company_urls').attr("src",res.companyDto.companyURL);

         //------------- 初始化分类-------------
         html = '';
         var storeExclusiveAssortDtoList = res.storeConstructionDto.storeExclusiveAssortDtoList;
         if (storeExclusiveAssortDtoList.length > 0) {
            for (var i = 0; i < storeExclusiveAssortDtoList.length; i++) {
               if (i == 0) {
                  html += '<a class="cur" onclick="clickAssortName(this)">' + storeExclusiveAssortDtoList[i].assortName + ' <input hidden name="exclusiveAssortId" value="' + storeExclusiveAssortDtoList[i].exclusiveAssortId + '"></a>';
                  //------------- 初始化产品-------------
                  var param = {
                     exclusiveAssortId: storeExclusiveAssortDtoList[i].exclusiveAssortId
                  }
                  initProductList(param);
               } else {
                  html += '<a onclick="clickAssortName(this)">' + storeExclusiveAssortDtoList[i].assortName + ' <input hidden name="exclusiveAssortId" value="' + storeExclusiveAssortDtoList[i].exclusiveAssortId + '"></a>';
               }
            }
            html += '<div class="product-search" id="product-search">\n' +
                  '        <input id="searchPName" type="text" placeholder="店内搜索">\n' +
                  '        <button type="button" class="am-btn am-btn-warning" onclick="searchP()">搜索</button>\n' +
                  '    </div>';

            $('#product-title').empty();
            $('#product-title').append(html);
         } else {
            // todo
         }

      }
   });

}

function clickAssortName(obj) {
   $(obj).parent().find('a').removeClass("cur");
   $(obj).addClass('cur');
   searchP()
}

function searchP() {
   var exclusiveAssortId = $('#product-title').find('.cur').children('input').val();
   var param = {
      productName: $("#searchPName").val(),
      exclusiveAssortId: exclusiveAssortId
   }
   initProductList(param);
}

function initProductList(param) {

   $.ajax({
      url: '/store/searchProduct',
      dataType: 'json',
      type: 'POST',
      data: JSON.stringify(param),
      contentType: "application/json; charset=utf-8",
      success: function (data) {
         initTable(data);
      }
   })
}

function initTable(data) {
   var html = '';
   if (data.length > 0) {
      for (var i = 0; i < data.length; i++) {
         html += '<dl>\n' +
               '        <dt class="pro-pic1"><img src="' + data[i].productDto.productUrl + '"></dt>\n' +
               '        <dd class="title1">' + data[i].productDto.productName + '</dd>\n' +
               '        <dd class="title2">￥<span style="font-size: 18px;">' + data[i].productDto.productStatisticsDto.productPrice + '</span></dd>\n' +
               '    </dl>';
      }
   } else {
      html += '<div style="padding-top: 25px">没有检索到相匹配的产品！</div>';
   }
   $('#product').empty();
   $('#product').append(html);
}


function switchPage(obj, type) {
   $(obj).parent().find('li').removeClass('cur');
   $(obj).addClass('cur');
   switch (type) {
      case 1:
         $('#shop-product').show();
         $('#shop-chart').show();
         $('#company').hide()
         break;
      case 2:
         $('#shop-product').hide();
         $('#shop-chart').hide();

         $('#company').show()


         break;
   }
}












