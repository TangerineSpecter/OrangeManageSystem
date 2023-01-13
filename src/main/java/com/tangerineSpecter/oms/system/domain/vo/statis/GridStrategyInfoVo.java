package com.tangerinespecter.oms.system.domain.vo.statis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("网格策略模型")
public class GridStrategyInfoVo implements Serializable {

    @Min(value = 1, message = "网格最小为1")
    @ApiModelProperty("网格数")
    private Integer number;
    @ApiModelProperty("下跌买入比例")
    private BigDecimal buyRate;
    @ApiModelProperty("上涨卖出比例")
    private BigDecimal sellRate;
}
