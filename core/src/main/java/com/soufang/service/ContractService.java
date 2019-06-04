package com.soufang.service;

import com.soufang.base.dto.contract.ContractDto;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public interface ContractService {

    void createContract(ContractDto contractDto);

    void uploadContract(ContractDto contractDto);

    ContractDto getDetail(Long contractId);

    ContractDto getDetail(String orderNumber ,Long orderShopId);

}
