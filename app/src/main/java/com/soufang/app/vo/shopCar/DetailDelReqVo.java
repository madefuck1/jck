package com.soufang.app.vo.shopCar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DetailDelReqVo {
    List<Long> shopCarProductIds;
}
