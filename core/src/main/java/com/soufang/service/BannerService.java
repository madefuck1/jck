package com.soufang.service;

import com.soufang.base.dto.banner.BannerDto;
import com.soufang.model.Banner;

import java.util.List;

public interface BannerService {

    /**
     * 上传图片后保存图片地址
     * @param bannerDto
     * @return
     */
    int addImg(BannerDto bannerDto);

    /**
     * 得到所有图片集合
     * @return
     */
    List<BannerDto> getList();

    /**
     * 删除
     * @return
     */
    int deleteById(Long id);
}
