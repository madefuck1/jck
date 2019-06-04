package com.soufang.mapper;

import com.soufang.model.Contract;

public interface ContractMapper {
    int deleteByPrimaryKey(Long contractId);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Long contractId);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);

    Contract selectByOrderNumberAndOrderShopId(Contract contract);
}