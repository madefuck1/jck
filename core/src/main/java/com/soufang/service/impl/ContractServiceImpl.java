package com.soufang.service.impl;

import com.soufang.base.dto.contract.ContractDto;
import com.soufang.base.enums.ContractStatusEnum;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.ContractMapper;
import com.soufang.model.Contract;
import com.soufang.service.ContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createContract(ContractDto contractDto) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto,contract);
        contract.setContractStatus(ContractStatusEnum.to_upload.getValue());
        contract.setCreateTime(DateUtils.getToday());
        contractMapper.insert(contract);
    }

    @Override
    public void uploadContract(ContractDto contractDto) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto,contract);
        contract.setContractStatus(ContractStatusEnum.to_confirm.getValue());
        contractMapper.updateByPrimaryKey(contract);
    }

    @Override
    public ContractDto getDetail(Long contractId) {
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        ContractDto contractDto = new ContractDto();
        BeanUtils.copyProperties(contract,contractDto);
        return contractDto;
    }

    @Override
    public ContractDto getDetail(String orderNumber, Long orderShopId) {
        Contract contract = new Contract();
        contract.setOrderShopId(orderShopId);
        contract.setOrderNumber(orderNumber);
        contract = contractMapper.selectByOrderNumberAndOrderShopId(contract);
        ContractDto contractDto = new ContractDto();
        if(contract != null){
            BeanUtils.copyProperties(contract,contractDto);
        }
        return  contractDto;
    }
}
