package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 轮询-报价和招募功能
 *
 * @author
 * @create 2019/5/14
 * @since 1.0.0
 */
@RestController
@RequestMapping("/core/enquiry")
public class EnquiryController {

    @Autowired
    EnquiryService enquiryService;
    @Autowired
    UserService userService;
    @Autowired
    EnquiryProductService enquiryProductService;
    @Autowired
    AssortService assortService;
    @Autowired
    SysParamService sysParamService;
    @Autowired
    PurchaseService purchaseService;

   /* @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getList(@RequestBody EnquirySo enquirySo){
        PageHelp<EnquiryDto> pageHelp = new PageHelp<>();
        List<EnquiryDto> list = enquiryService.getList(enquirySo);
        int count = enquiryService.getCount(enquirySo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }*/

    @RequestMapping(value = "getByNum",method = RequestMethod.POST)
    EnquiryDto getByNum(@RequestBody String enquiryNumber){
        EnquirySo enquirySo = new EnquirySo();
        enquirySo.setEnquiryNumber(enquiryNumber);
        return enquiryService.getByEnqNum(enquirySo);
    }
    @RequestMapping(value = "getByEnquiryNumber",method = RequestMethod.POST)
    UserDto getByEnquiryNumber(@RequestBody String enquiryNumber){
         UserDto userDto = userService.getByEnquiryNumber(enquiryNumber);
        return userDto;
    }
    @RequestMapping(value = "getEPD",method = RequestMethod.POST)
    List<EnquiryProductDto> getEPD(@RequestBody String enquiryNumber){
        return enquiryProductService.getByEnquiryNumber(enquiryNumber);
    }
    /*@RequestMapping(value = "banQuote",method = RequestMethod.POST)
    Result banQuote(@RequestBody String enquiryNumber){
        Result result = new Result();
        if(enquiryService.banQuote(enquiryNumber) > 0){
            result.setSuccess(true);
            result.setMessage("禁用成功");
        }else {
            result.setSuccess(false);
            result.setMessage("禁用失败");
        }
        return result;
    }*/

    /*@RequestMapping(value = "regainQuote",method = RequestMethod.POST)
    Result regainQuote(@RequestBody String enquiryNumber){
        Result result = new Result();
        if(enquiryService.regainQuote(enquiryNumber) > 0){
            result.setSuccess(true);
            result.setMessage("恢复成功");
        }else {
            result.setSuccess(false);
            result.setMessage("恢复失败");
        }
        return result;
    }*//*
    @RequestMapping(value = "getIdName",method = RequestMethod.POST)
    public List<Map<String, Object>> getIdName() {
        return assortService.getIdName();
    }*/

    /*@RequestMapping(value = "addEnquiry",method = RequestMethod.POST)
    String addEnquiry(@RequestBody  EnquiryDto enquiryDto){
        String enquiryNumber = "EN"+sysParamService.getOrderNumber();
        enquiryDto.setCreateTime(DateUtils.getToday());
        enquiryDto.setEnquiryNumber(enquiryNumber);
        System.out.println(enquiryDto);
        enquiryService.insertSelective(enquiryDto);
        return enquiryNumber;
    }*/
/*

    @RequestMapping(value = "addEnquiryProduct")
    Result addEnquiryProduct(@RequestBody List<EnquiryProductDto> listDto){
        Result result =new Result();
        if(enquiryProductService.addEnquiryProduct(listDto) > 0){
            result.setSuccess(true);
            result.setMessage("发布成功");
        }else{
            result.setSuccess(false);
            result.setMessage("发布失败");
        }
        return result;
    }
*/

    //商品编号
    @RequestMapping(value = "toGetEqNumber",method = RequestMethod.POST)
    public String toGetEqNumber(){
        //获取单号
        String enquiryNumber = "EN"+sysParamService.getOrderNumber();
        return enquiryNumber;
    }



    /**
     * 添加询盘信息
     * 需要对插入数据操作做事物
     * 需要对操作完成状态进行判断
     * @param enquiryDto，enquiryProductDto
     * @return result
     */
    @RequestMapping(value = "addEnquiry",method = RequestMethod.POST)
    public Result addEnquiry(@RequestBody  EnquiryDto enquiryDto){
        Result result =new Result();
        if(enquiryService.insertSelective(enquiryDto)>0){
            result.setSuccess(true);
            result.setMessage("发布成功");
        }else{
            result.setSuccess(false);
            result.setMessage("发布失败");
        }
        return result;
    }

    /**
     * 查询询盘列表信息
     * -分页带模糊标题查询
     * @param enquirySo
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getList(@RequestBody EnquirySo enquirySo) {
        PageHelp<EnquiryDto> pageHelp = new PageHelp<>();
        List<EnquiryDto> lists = enquiryService.getList(enquirySo);
        //总条数
        int count=enquiryService.enquiryTableCount(enquirySo);
        pageHelp.setData(lists);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @RequestMapping(value = "getIndexEnquiryList",method = RequestMethod.POST)
    PageHelp<EnquiryDto> getIndexEnquiryList(@RequestBody EnquirySo enquirySo){
        PageHelp<EnquiryDto> pageHelp = new PageHelp<>();
        List<EnquiryDto> lists = enquiryService.getIndexEnquiryList(enquirySo);
        int count =lists.size();
        pageHelp.setData(lists);
        pageHelp.setCount(count);
        return pageHelp;
    }


    /**
     * 获取我的报价列表
     * @param enquirySo
     * @return*/

    @RequestMapping(value = "getMyQuoteList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getMyQuoteList(@RequestBody EnquirySo enquirySo) {
        PageHelp<EnquiryDto> pageHelp = new PageHelp<>();
        List<EnquiryDto> list = enquiryService.getList(enquirySo);
        int count = enquiryService.getMyQuoteCount(enquirySo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }
    @RequestMapping(value = "getQuoteDetails",method = RequestMethod.POST)
    public EnquiryDto getQuoteDetails(@RequestBody Long enquiryProductId){
        EnquiryDto enquiryDto =  enquiryService.getQuoteDetails(enquiryProductId);
        return enquiryDto;
    }
    /**
     * 查询询盘信息详情
     * -包括所有询盘产品信息-询盘报价-询盘商铺信息
     * @param enquirySo
     * @return JSONArray
     */
    @RequestMapping(value = "selEnquiryByNumber",method = RequestMethod.POST)
    public EnquiryDto selEnquiryByNumber(@RequestBody EnquirySo enquirySo){
        //1.询盘-询盘产品对象集合-报价信息-商铺信息信息对象
        EnquiryDto enquiryDto=  enquiryService.getByEnqNum(enquirySo);
        return enquiryDto;
    }

    /**
     * 修改当前询盘信息
     * -1.修改询盘基础信息
     * -2.修改产品信息
     * @param enquiryDto 、enquiryProductDto
     * @return Result
     */
    @RequestMapping(value = "updateEnquiryAndProduct",method = RequestMethod.POST)
    public Result updateEnquiryAndProduct(@RequestBody EnquiryDto enquiryDto){
        Result result=new Result();
        //修改询盘信息
        int i =enquiryService.updateEnquiry(enquiryDto);
        //修改产品信息
        //enquiryProductService.updateEnquiryProduct(enquiryDto.getEnquiryProductDto());
        if(i>0){
            result.setSuccess(true);
            result.setMessage("发布成功");
            //需要回滚操作
        }else{
            result.setSuccess(false);
            result.setMessage("发布失败");
       }
        return result;
    }

    /**
     * 修改状态为-停用
     * @param enquiryNumber
     * @return result
     */
    @RequestMapping(value = "banQuote",method = RequestMethod.POST)
    public Result banQuote(@RequestBody String enquiryNumber){
        Result result = new Result();
        if(enquiryService.banQuote(enquiryNumber) > 0){
            result.setSuccess(true);
            result.setMessage("禁用成功");
        }else {
            result.setSuccess(false);
            result.setMessage("禁用失败");
        }
        return result;
    }
    //停用，恢复，删除可以整合为一个功能，页面直接判定即可。


    /**
     * 恢复
     * @param enquiryNumber
     * @return
     */
    @RequestMapping(value = "regainQuote",method = RequestMethod.POST)
    public Result regainQuote(@RequestBody String enquiryNumber){
        Result result = new Result();
        if(enquiryService.regainQuote(enquiryNumber) > 0){
            result.setSuccess(true);
            result.setMessage("恢复成功");
        }else {
            result.setSuccess(false);
            result.setMessage("恢复失败");
        }
        return result;
    }

    /**
     * 删除询盘功能
     * @param enquiryNumber
     * @return
     */
    @RequestMapping(value = "delEnquiry",method = RequestMethod.POST)
    public Result delEnquiry(@RequestBody String enquiryNumber){
        Result result = new Result();
        int i = enquiryService.delEnquiry(enquiryNumber);
        if(i> 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 查询询盘用户信息
     * @param enquiryNumber
     * @return
     */
    @RequestMapping(value = "selUserIdByEnquiryNumber",method = RequestMethod.POST)
    public Long selUserIdByEnquiryNumber(@RequestBody String enquiryNumber){
        return  enquiryService.selUserIdByEnquiryNumber(enquiryNumber);
    }

}
