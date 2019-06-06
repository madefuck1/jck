package com.soufang.app.feign;

import com.soufang.base.dto.address.AddressDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;
@FeignClient("core")
public interface AssortFeign {
    @RequestMapping(value = "/core/assort/getAssortIdByName",method = RequestMethod.POST)
    BigInteger getAssortIdByName(@RequestBody String assortName);
}
