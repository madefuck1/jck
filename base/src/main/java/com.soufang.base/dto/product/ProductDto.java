package com.soufang.base.dto.product;

import com.soufang.base.PageBase;
import com.soufang.base.PropertiesParam;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.StaticContant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class ProductDto extends PageBase implements Serializable {

    private Long productId;
    //    店铺id
    private Long shopId;
    //    店铺名称
    private String shopName;

    private String productName;

    private Integer isUpper;
    // 分类id
    private Long assortId;
    // 分类名称
    private String assortName;

    private String productJson;

    private String productUnit;

    private String productDetail;

    private String productImage;
    private String productUrl;

    private Integer productLevel;

    private String productSendCountry;

    private String productSendProvince;

    private String productSendCity;

    private Date createTime;

    private String kv1;

    private String key1;
    private String key1Value;

    private String kv2;

    private String key2;
    private String key2Value;

    private String kv3;

    private String key3;
    private String key3Value;

    private String kv4;

    private String key4;
    private String key4Value;

    private String kv5;

    private String key5;
    private String key5Value;

    private String createTimeString;

    private List<ProductColorDto> productColorDtoList;

    private List<ProductSpecDto> productSpecDtoList;

    private ProductStatisticsDto productStatisticsDto;

    //封装spec
    private List<SpecDto> specDtoList ;

    public String getCreateTimeString() {
        return createTime == null ? StaticContant.defaultDate : DateUtils.date2String(createTime, DateUtils.format1);
    }
    public String getProductUrl() {
        return PropertiesParam.file_pre + productImage;
    }


    // 产品具体对应规格数量的信息
    private ProductSpecDto productSpecDto;
    // 产品具体对应规格数量的信息 对应检索参数
    private String productSpec; // 规格
    private Long productNumber; // 数量

    // 是否被浏览过标记
    private Boolean isScan;
    // 检索产品，用户是否已登录
    private Long userId;


    // 检索条件
    // 产品名称
    // private String productName;
    // 价格排序  （0:正序;1:倒序）
//    private Integer priceSort;
//    // 销量排序  （0:正序;1:倒序）
//    private Integer sellSort;
//     分类id
     private String assortIds;
    // 起始价格
    //    private BigDecimal minPrice;
    //    // 结束价格
    //    private BigDecimal maxPrice;
    // 发货地
    // private String productSendCity;
// 排序 （0:正序;1:倒序）
    private Integer sort;
    // 排序类型 (1:销量；2：浏览量；3：价格)
    private Integer sortType;

    // app 产品对应最低价格
    private BigDecimal productPrice;
}
