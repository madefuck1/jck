package com.soufang.base.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/16
 * @Description:
 */
@Getter
@Setter
public class SpecDto {

    private String specName;

    private Long min;

    private List<SpecDetailDto> specDetailDtoList;

}
