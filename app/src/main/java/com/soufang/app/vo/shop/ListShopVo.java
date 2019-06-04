package com.soufang.app.vo.shop;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.shop.ShopDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListShopVo extends AppVo {
    List<ShopDto> data;
}
