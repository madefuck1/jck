package com.soufang.mapper;

import com.soufang.base.dto.banner.BannerDto;
import com.soufang.model.Banner;

import java.util.List;

public interface BannerMapper {

    /**
     * 上传图片后保存图片地址
     * @param banner
     * @return
     */
    int addImg(Banner banner);

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
