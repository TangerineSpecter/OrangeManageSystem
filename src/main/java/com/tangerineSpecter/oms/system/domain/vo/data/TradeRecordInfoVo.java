package com.tangerinespecter.oms.system.domain.vo.data;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeRecordInfoVo implements Serializable {

    @NotNull(groups = {Update.class}, message = "id不能为空")
    @ApiModelProperty("交易数据id")
    private Long id;
    @ApiModelProperty("开盘资金")
    private Double startMoney;
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
    @ApiModelProperty("转出金额")
    private Double withdrawal;
}
