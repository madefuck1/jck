package com.soufang.base.dto.storeConstruction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class StoreExclusiveAssortDto {

    private Long exclusiveAssortId;

    private Long shopId;

    private String assortName;

    private String sortName;

    private Integer isShow;

    private String exclusiveAssortIds;
}