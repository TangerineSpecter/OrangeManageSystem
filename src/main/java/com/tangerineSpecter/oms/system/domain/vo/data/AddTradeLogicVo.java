package com.tangerinespecter.oms.system.domain.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTradeLogicVo {

    @NotNull(message = "开仓时间不能为空")
    private String entryDate;
    @NotNull(message = "交易名称不能为空")
    private String name;
    @NotNull(message = "入场点不能为空")
    private Double entryPoint;
    @NotNull(message = "止盈点不能为空")
    private Double profitPoint;
    @NotNull(message = "止损点不能为空")
    private Double lossPoint;
    @NotBlank(message = "交易逻辑不能为空")
    private String tradeLogic;
    @NotNull(message = "类型不能为空")
    private Integer type;
}
