package com.tangerinespecter.oms.system.domain.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("交易计算模型")
public class RecordCalStatisDto implements Serializable {

    @ApiModelProperty("记录时间")
    private String date;
    @ApiModelProperty("累计收盘金额")
    private Integer totalEndMoney;
    @ApiModelProperty("总收益")
    private Integer totalIncome;
    @ApiModelProperty("总投入金额，入金 - 出金")
    private Integer totalInputMoney;
}
