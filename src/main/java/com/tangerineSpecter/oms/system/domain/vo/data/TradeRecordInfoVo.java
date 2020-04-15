package com.tangerinespecter.oms.system.domain.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeRecordInfoVo {

    private Long id;
    @NotNull(message = "开盘资金不能为空")
    private Integer startMoney;
    @NotNull(message = "收盘资金不能为空")
    private Integer endMoney;
    @NotBlank(message = "交易时间不能为空")
    private String date;
    @NotNull(message = "类型不能为空")
    private Integer type;
}
