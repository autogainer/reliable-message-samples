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
@Table(name = "t_pay_order")
public class PayOrder {
    /**
     * 支付订单ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 业务系统订单ID
     */
    @Column(name = "business_order_id")
    private Integer businessOrderId;

    /**
     * 支付金额
     */
    private BigDecimal money;

    /**
     * 支付状态 0未支付 1已支付  2 支付失败
     */
    private Byte status;

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