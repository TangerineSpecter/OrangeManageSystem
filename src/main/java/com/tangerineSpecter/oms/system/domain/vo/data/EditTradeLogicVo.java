package com.tangerinespecter.oms.system.domain.vo.data;

import io.swagger.annotations.ApiModelProperty;
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
public class EditTradeLogicVo {

    @NotNull(message = "id不能为空")
    private Long id;
    @NotNull(message = "平仓时间不能为空")
    @ApiModelProperty("平仓时间")
    private String exitDate;
    @NotNull(message = "盈亏状态不能为空")
    @ApiModelProperty("盈亏状态（-1：未平仓；0：盈利；1：亏损）")
    private Integer status;
    @NotNull(message = "入场点不能为空")
    @ApiModelProperty("入场点")
    private Double entryPoint;
    @NotNull(message = "出场点不能为空")
    @ApiModelProperty("出场点")
    private Double exitPoint;
    @ApiModelProperty("当日收盘价")
    private Double closingPrice;
    @NotBlank(message = "交易结论不能为空")
    @ApiModelProperty("交易结论")
    private String conclusion;
}
