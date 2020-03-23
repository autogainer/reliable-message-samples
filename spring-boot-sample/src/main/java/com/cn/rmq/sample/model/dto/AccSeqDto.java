package com.cn.rmq.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author xul
 * @date 2020/3/16.
 */
@ApiModel
@Data
public class AccSeqDto implements Serializable {
    @ApiModelProperty(value = "账户ID", required = true)
    @NotNull
    @Min(1)
    private Integer accId;

    @ApiModelProperty(value = "记账金额", required = true)
    @NotNull
    @Min(1)
    private BigDecimal money;

    @ApiModelProperty(value = "业务流水号", required = true)
    @NotNull
    @Min(1)
    private Integer bizNo;
}
