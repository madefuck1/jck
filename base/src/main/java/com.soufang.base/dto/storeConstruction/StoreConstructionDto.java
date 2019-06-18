package com.soufang.base.dto.storeConstruction;

import com.soufang.base.PropertiesParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StoreConstructionDto {

    private Long storeConstructionId;

    private Long shopId;

    private String storeLogo;
    private String storeLogoUrl;

    private String storeNavColor;

    private Integer storeStatus;

    private List<StoreCurouselMapDto> storeCurouselMapDtoList;

    private List<StoreExclusiveAssortDto> storeExclusiveAssortDtoList;

    public String getStoreLogoUrl() {
        return storeLogo == null ? "" : PropertiesParam.file_pre + storeLogo;
    }


}
