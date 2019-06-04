package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.contract.ContractDto;
import com.soufang.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chen
 * @Date: 2019/5/23
 * @Description:
 */
@RestController
@RequestMapping("/core/contract")
public class ContractController {

    @Autowired
    ContractService contractService;

    @RequestMapping(value = "getDetail" ,method = RequestMethod.POST)
    public ContractDto getDetail(@RequestBody ContractDto contractDto){
        return contractService.getDetail(contractDto.getOrderNumber(),contractDto.getOrderShopId());
    }


    @RequestMapping(value = "down" ,method = RequestMethod.POST)
    public Result down(@RequestBody ContractDto contractDto){
        contractService.createContract(contractDto);
        return  new Result();
    }

    @RequestMapping(value = "upload" , method = RequestMethod.POST)
    public Result upload(@RequestBody ContractDto contractDto){
        contractService.uploadContract(contractDto);
        return  new Result();
    }
}
