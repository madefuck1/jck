/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FootPrintAdd
 * Author:   小三
 * Date:     2019/5/22 17:21
 * Description: 增加足迹
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.footPrint;

import com.soufang.app.vo.AppVo;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈增加足迹〉
 *
 * @author 小三
 * @create 2019/5/22
 * @since 1.0.0
 */
public class FootPrintAddVo extends AppVo {
    private Long footprintId;

    private Long userId;

    private Long productId;

    private Long shopId;

    private Date createTime;

    public Long getFootprintId() {
        return footprintId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getShopId() {
        return shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setFootprintId(Long footprintId) {
        this.footprintId = footprintId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
