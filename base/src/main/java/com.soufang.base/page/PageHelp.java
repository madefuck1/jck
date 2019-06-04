package com.soufang.base.page;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
public class PageHelp<E> {

    List<E> data;
    int count;

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
