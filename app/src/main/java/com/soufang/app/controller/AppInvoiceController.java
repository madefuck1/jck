package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppInvoiceFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.invoice.InvoiceAddVo;
import com.soufang.app.vo.invoice.InvoiceVo;
import com.soufang.app.vo.invoice.UpdateInvoiceVo;
import com.soufang.base.Result;
import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.base.dto.user.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("app/invoice/")
public class AppInvoiceController extends AppBaseController{

    @Autowired
    AppInvoiceFeign appInvoiceFeign;

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public InvoiceVo getInvoiceList(HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        InvoiceVo invoiceVo = new InvoiceVo();
        List<InvoiceDto> listDto = appInvoiceFeign.getList(userInfo.getUserId());
        if(listDto != null && listDto.size() > 0){
            invoiceVo.setData(listDto);
            invoiceVo.setSuccess(true);
            invoiceVo.setMessage("查询成功");
        }else {
            invoiceVo.setData(null);
            invoiceVo.setSuccess(false);
            invoiceVo.setMessage("对不起！您没有相关的发票信息");
        }
        return invoiceVo;
    }

    //新增发票
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public InvoiceVo saveInvoice(HttpServletRequest request , @RequestBody InvoiceAddVo invoiceAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setUserId(userInfo.getUserId());//设置用户id
        BeanUtils.copyProperties(invoiceAddVo,invoiceDto);
        Result result = appInvoiceFeign.saveInvoice(invoiceDto);
        return judge(result);
    }
    //更新发票
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public InvoiceVo updateInvoice(HttpServletRequest request , @RequestBody UpdateInvoiceVo updateInvoiceVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setUserId(userInfo.getUserId());//设置用户id
        BeanUtils.copyProperties(updateInvoiceVo,invoiceDto);
        Result result = appInvoiceFeign.update(invoiceDto);
        return judge(result);
    }
    //删除发票
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "delete/{invoiceId}",method = RequestMethod.POST)
    public InvoiceVo delete(@PathVariable Long invoiceId ){
        Result result = appInvoiceFeign.delete(invoiceId);
        return judge(result);
    }
    //设为默认发票
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "setDefault/{invoiceId}",method = RequestMethod.POST)
    public InvoiceVo updatedDefaultInvoice(@PathVariable Long invoiceId){
        Result result = appInvoiceFeign.updatedDefaultInvoice(invoiceId);
        return judge(result);
    }

    private InvoiceVo judge(Result result){
        InvoiceVo vo = new InvoiceVo();
        if(result.isSuccess()){
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }else {
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }
        return vo;
    }


}
