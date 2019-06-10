package com.soufang.base.dto.storeConstruction;

import com.soufang.base.PropertiesParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class StoreCurouselMapDto {
    private Long storeCurouselMapId;

    private Long storeConstructionId;

    private Long productId;

    private String curouselMapUrl;
    private String mapURL;

    private String storeCurouselMapLink;
    public String getMapURL() {
        return curouselMapUrl == null ? "" : PropertiesParam.file_pre + curouselMapUrl;
    }
}