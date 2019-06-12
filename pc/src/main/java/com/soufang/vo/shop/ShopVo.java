package com.soufang.vo.shop;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/6/12
 * @Description:
 */
@Getter
@Setter
public class ShopVo extends BaseVo {

    private List<ShopDto> list;
    private int count ;
}
