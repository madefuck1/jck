package com.soufang.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageBase {
    private Integer page;
    private Integer limit;

    public PageBase(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public PageBase() {
    }
}
