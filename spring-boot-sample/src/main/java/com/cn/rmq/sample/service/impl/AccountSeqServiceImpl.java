package com.cn.rmq.sample.service.impl;

import com.cn.rmq.sample.mapper.AccountSeqMapper;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.po.AccountSeq;
import com.cn.rmq.sample.service.IAccountSeqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 帐户服务实现
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class AccountSeqServiceImpl extends BaseServiceImpl<AccountSeqMapper, AccountSeq, Integer>
        implements IAccountSeqService {


    @Override
    public void creataccSeq(AccSeqDto req) {
        log.info("【createaccSeq】start:{}", req);
        Date now = new Date();

        AccountSeq accountSeq = new AccountSeq();
        BeanUtils.copyProperties(req, accountSeq);
        accountSeq.setCreateTime(now);
        accountSeq.setUpdateTime(now);
        mapper.insertSelective(accountSeq);

        log.info("accSeq is[{}]" + accountSeq);
    }

}
