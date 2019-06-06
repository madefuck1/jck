package com.soufang.base.dto.storeConstruction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class StoreCurouselMapDto {
    private Long storeCurouselMapId;

    private Long storeConstructionId;

    private Long productId;

    private String curouselMapUrl;

    private String storeCurouselMapLink;
}