package com.cn.rmq.sample.service.impl;

import com.cn.rmq.sample.mapper.AccountMapper;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.po.Account;
import com.cn.rmq.sample.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 帐户服务实现
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
@Slf4j
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account, Integer>
        implements IAccountService {

    @Reference
    private IAccountService accountService;

    @Override
    public void changeMoney(AccSeqDto accSeqDto) {

        Account account = accountService.selectByPrimaryKey(accSeqDto.getAccId());
        if(account == null){
            log.error("【changeMoney】[{}]账户不存在", accSeqDto.getAccId());
            throw new RuntimeException("账户不存在");
        }

        //增加账户余额
        Account updateAccount = new Account();
        updateAccount.setId(accSeqDto.getAccId());
        updateAccount.setMoney(accSeqDto.getMoney());
        mapper.changeMoney(updateAccount);
    }
}
