package com.tangerinespecter.oms.system.domain.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditTradeLogicVo {

    @NotNull(message = "id不能为空")
    private Long id;
    @NotNull(message = "平仓时间不能为空")
    private String exitDate;
    @NotNull(message = "盈亏状态不能为空")
    private Integer status;
    @NotNull(message = "入场点不能为空")
    private Double entryPoint;
    @NotNull(message = "出场点不能为空")
    private Double exitPoint;
    private Double closingPrice;
    @NotBlank(message = "交易结论不能为空")
    private String conclusion;
}
