package com.soufang.model;

import java.util.Date;

public class Banner {
    private  long bannerId;
    private int terminal;
    private String bannerImage;
    private int sort;
    private int isDelete;
    private Date createTime;

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Banner() {
    }

    public Banner(long bannerId, int terminal, String bannerImage, int sort, int isDelete, Date createTime) {
        this.bannerId = bannerId;
        this.terminal = terminal;
        this.bannerImage = bannerImage;
        this.sort = sort;
        this.isDelete = isDelete;
        this.createTime = createTime;
    }
}
