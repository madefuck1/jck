package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.banner.BannerDto;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.BannerMapper;
import com.soufang.model.Banner;
import com.soufang.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "bannerService")
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;

    /**
     * 添加banner图片的信息
     * @param bannerDto
     * @return
     */
    @Override
    public int addImg(BannerDto bannerDto) {
        Banner banner = new Banner();
        banner.setBannerImage(bannerDto.getBannerImage());
        banner.setCreateTime(DateUtils.getToday());
        banner.setTerminal(bannerDto.getTerminal());
        banner.setIsDelete(0);
        banner.setSort(1);
        bannerMapper.addImg(banner);
        return 1;
    }

    /**
     * 得到所有的图片
     * @return
     */
    @Override
    public List<BannerDto> getList(Integer terminal) {
        Banner banner = new Banner();
        banner.setTerminal(terminal);
        List<BannerDto> list = bannerMapper.getList(banner);
        //设置图片地址
        for (BannerDto b :list) {
            b.setBannerImage(PropertiesParam.file_pre+b.getBannerImage());
        }
        return list;
    }

    /**
     * 根据图片banner id 删除图片信息
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        bannerMapper.deleteById(id);
        return 1;
    }

    @Override
    public List<BannerDto> getAssortPicture(BannerDto bannerDto) {
        List<BannerDto> list = bannerMapper.getAssortPicture(bannerDto);
        //设置图片地址
        for (BannerDto b :list) {
            b.setBannerImage(PropertiesParam.file_pre+b.getBannerImage());
        }
        return list;
    }
}
