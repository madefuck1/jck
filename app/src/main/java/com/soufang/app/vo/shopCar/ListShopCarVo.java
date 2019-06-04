package com.soufang.app.vo.shopCar;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.shopCar.ShopCarDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListShopCarVo extends AppVo {
    List<ShopCarDto> data;
}
