package com.soufang.mapper;

import com.soufang.model.Pay;

import java.util.List;

public interface PayMapper {
    int deleteByPrimaryKey(Long payId);

    int insert(Pay record);

    int insertSelective(Pay record);

    Pay selectByPrimaryKey(Long payId);

    int updateByPrimaryKeySelective(Pay record);

    int updateByPrimaryKey(Pay record);

    List<Pay> selectByOrderNumberAndStatus(Pay pay);
}