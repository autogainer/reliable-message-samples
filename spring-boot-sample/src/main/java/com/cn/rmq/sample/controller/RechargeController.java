package com.cn.rmq.sample.controller;

import cn.hutool.json.JSONObject;
import com.cn.rmq.sample.model.dto.BaseRsp;
import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.service.IRechargeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Chen Nan
 */
@RestController
@Api(tags = "发起充值业务", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/recharge")
@Slf4j
public class RechargeController {

    @Reference
    private IRechargeOrderService rechargeOrderService;


    @ApiOperation("创建充值订单入库，同时生成支付单入库，发送预发送记账报文")
    @PostMapping
    public Object add(@ModelAttribute @Valid RechargeDto req) {
        BaseRsp rsp = new BaseRsp();
        String MsgAttr = rechargeOrderService.createRechargeOrder(req);
        JSONObject json = new JSONObject(MsgAttr);
        rsp.setData(json);
        return rsp;
    }
}
