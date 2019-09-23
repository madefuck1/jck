package com.soufang.vo.StoreConstruction;

import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.storeConstruction.StoreViewDto;
import com.soufang.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DetailStoreConstructionVo extends BaseVo {

    private StoreConstructionDto storeConstructionDto;

    private ShopDto shopDto;

    private CompanyDto companyDto;

    private Boolean isCollection;

    private List<StoreViewDto> storeViewDtos;
}
