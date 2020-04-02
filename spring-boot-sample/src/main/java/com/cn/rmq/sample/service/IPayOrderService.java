package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.PayDto;
import com.cn.rmq.sample.model.po.PayOrder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 支付订单服务接口
 *
 * @author Chen Nan
 */
@Path("IPayOrderService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IPayOrderService extends IBaseService<PayOrder, Integer> {
    /**
     * 支付成功
     * @param req 支付订单ID
     */
    void paySuccess(PayDto req);

    /**
     * 支付结果处理
     * @param req 支付订单ID/结果
     */
    @GET
    @Path("/payResultProcess")
    void payResultProcess(PayDto req);

    /**
     * 确认业务处理结果
     * @param req 支付订单信息
     * @return 是否成功 1是 0否
     */
    int check(PayOrder req);
}
