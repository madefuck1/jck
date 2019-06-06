package com.soufang.base.dto.storeConstruction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoreConstructionDto {

    private Long storeConstructionId;

    private Long shopId;

    private String storeLogo;

    private String storeNavColor;

    private Integer storeStatus;
}
