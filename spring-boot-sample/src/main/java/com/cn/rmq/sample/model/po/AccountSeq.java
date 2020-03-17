package com.cn.rmq.sample.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "t_account_seq")
public class AccountSeq {
    /**
     * 流水id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 账户id
     */
    @Column(name = "acc_id")
    private Integer accId;

    /**
     * 账户余额
     */
    private BigDecimal money;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}