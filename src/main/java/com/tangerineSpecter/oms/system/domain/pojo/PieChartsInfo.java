package com.tangerinespecter.oms.system.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("饼图模型")
public class PieChartsInfo implements Serializable {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("值")
    private BigDecimal value;
}
