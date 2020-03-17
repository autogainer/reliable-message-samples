package com.cn.rmq.sample.service;

import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.po.AccountSeq;

/**
 * 账户服务接口
 *
 * @author Chen Nan
 */
public interface IAccountSeqService extends IBaseService<AccountSeq, Integer> {

    /**
     * 创建账务流水
     * @param req 账户流水信息
     */
    void creataccSeq(AccSeqDto req);
}
