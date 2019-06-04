/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: PurchaseVo
 * Author:   小三
 * Date:     2019/6/1 9:43
 * Description: 报价信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.purchase;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.purchase.PurchaseDto;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈报价信息〉
 *
 * @author 小三
 * @create 2019/6/1
 * @since 1.0.0
 */
public class PurchaseVo extends AppVo{

    private List<PurchaseDto> data;

    public List<PurchaseDto> getData() {
        return data;
    }

    public void setData(List<PurchaseDto> data) {
        this.data = data;
    }
}
