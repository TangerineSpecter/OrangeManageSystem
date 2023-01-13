package com.tangerinespecter.oms.system.domain.vo.statis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("基金分析模型")
public class FundAnalysisInfoVo implements Serializable {

    @NotNull(message = "名称不能为空")
    @ApiModelProperty("基金代码")
    private String code;

    @ApiModelProperty("起始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("起始资金")
    private BigDecimal money;

    @ApiModelProperty("网格策略设置")
    private GridStrategyInfoVo gridSetting;
}
