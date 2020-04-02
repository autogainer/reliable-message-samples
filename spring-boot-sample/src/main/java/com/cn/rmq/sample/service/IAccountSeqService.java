package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.po.AccountSeq;

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
@Path("IAccountSeqService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface IAccountSeqService extends IBaseService<AccountSeq, Integer> {

    /**
     * 创建账务流水
     * @param req 账户流水信息
     */
    @GET
    @Path("/creataccSeq")
    void creataccSeq(AccSeqDto req);
}
