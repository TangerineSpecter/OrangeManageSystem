package com.tangerinespecter.oms.system.domain.vo.data;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("交易信息模型")
public class TradeRecordInfoVo implements Serializable {

    @NotNull(groups = {Update.class}, message = "更新时，id不能为空")
    @ApiModelProperty("交易数据id")
    private Long id;
    @NotNull(message = "收盘资金不能为空")
    @ApiModelProperty("收盘资金")
    private Double endMoney;
    @NotBlank(message = "交易时间不能为空")
    @ApiModelProperty("交易时间")
    private String date;
    @NotNull(message = "类型不能为空")
    @ApiModelProperty("交易类型")
    private Integer type;
    @NotNull(message = "币种不能为空")
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("转入金额")
    private Double deposit;
    @ApiModelProperty("转入金额汇率")
    private BigDecimal depositRate;
    @ApiModelProperty("转出金额")
    private Double withdrawal;
    @ApiModelProperty("转出金额汇率")
    private BigDecimal withdrawalRate;
    @ApiModelProperty("备注")
    private String remark;
}
