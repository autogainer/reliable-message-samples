package com.cn.rmq.sample.mq;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.model.RmqMessage;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.sample.model.Constants;
import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.service.IAccountSeqService;
import com.cn.rmq.sample.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/31.
 */
@Component
@Slf4j
public class PayQueueCustomer {

    @Reference
    private IAccountSeqService accountSeqService;
    @Reference
    private IAccountService accountService;
    @Reference
    private IRmqService rmqService;

    @JmsListener(destination = Constants.QUEUE_PAY)
    public void handleMsg(RmqMessage msg) {
        try {
            log.info("【payQueue】下层业务处理账务消息逻辑" + msg);
            AccSeqDto accSeqDto = JSONUtil.toBean(msg.getMessageBody(), AccSeqDto.class);

            //插入记账流水,业务判重
            accountSeqService.creataccSeq(accSeqDto);
            //rechargeOrderService.rechargeSuccess(accountSeq);
            //rechargeOrderService.accProcess();
            //更新账务金额
            accountService.changeMoney(accSeqDto);
            // 通知RMQ消息消费成功
            // 如果使用的是RMQ的directSendMessage，无需通知
            if (StrUtil.isNotBlank(msg.getMessageId())) {

                rmqService.deleteMessageById(msg.getMessageId());

            }

            log.info("【payQueue】处理消息成功");
        } catch (Exception e) {
            log.error("【payQueue】Exception:", e);
        }
    }
}
