package com.soufang.app.vo.shop;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.shop.ShopDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailVo extends AppVo {
    private ShopDto data;
}
