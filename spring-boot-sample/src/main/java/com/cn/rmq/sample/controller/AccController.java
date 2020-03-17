package com.cn.rmq.sample.controller;

import com.cn.rmq.sample.model.dto.AccSeqDto;
import com.cn.rmq.sample.model.dto.BaseRsp;
import com.cn.rmq.sample.service.IAccountSeqService;
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
 * @author autogainer
 */
@RestController
@Api(tags = "创建记账单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/creataccseq")
@Slf4j
public class AccController {

    @Reference
    private IAccountSeqService iAccountSeqService;

    @ApiOperation("创建单笔记账流水")
    @PostMapping
    public Object add(@ModelAttribute @Valid AccSeqDto req) {
        BaseRsp rsp = new BaseRsp();
        iAccountSeqService.creataccSeq(req);
        return rsp;
    }
}
