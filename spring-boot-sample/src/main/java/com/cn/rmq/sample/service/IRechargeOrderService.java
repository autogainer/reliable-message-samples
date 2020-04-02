package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.RechargeDto;
import com.cn.rmq.sample.model.po.PayOrder;
import com.cn.rmq.sample.model.po.RechargeOrder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 充值订单服务接口
 *
 * @author Chen Nan
 */
@Path("IRechargeOrderService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IRechargeOrderService extends IBaseService<RechargeOrder, Integer> {
    /**
     * 创建充值订单
     * @param req 充值账户与金额
     * @return 支付订单ID
     */
    @GET
    @Path("/createRechargeOrder")
    String createRechargeOrder(RechargeDto req);


    /**
     * 充值成功业务处理
     * @param payOrder 支付订单信息
     */
    void rechargeSuccess(PayOrder payOrder);


}
