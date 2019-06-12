package com.soufang.base.dto.storeConstruction;

import com.soufang.base.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

public class StoreExclusiveAssortDto {

    private Long exclusiveAssortId;

    private Long shopId;

    private String assortName;

    private String sortName;

    private Integer isShow;

    private String exclusiveAssortIds;

    private Date createTime;
    private String createTimeString;

    public String getCreateTimeString() {
        return createTime == null ? "2001-01-01" : DateUtils.date2String(createTime, DateUtils.format1);
    }


}