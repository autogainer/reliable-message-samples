package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.po.Account;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 账户服务接口
 *
 * @author Chen Nan
 */
@Path("IAccountService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IAccountService extends IBaseService<Account, Integer> {
    /**
     * 修改账户金额
     * @param accSeqDto 账户信息
     */
    @GET
    @Path("/changeMoney")
    void changeMoney(AccSeqDto accSeqDto);
}
