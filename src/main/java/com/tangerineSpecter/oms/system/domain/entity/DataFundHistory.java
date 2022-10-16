package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 基金历史数据
 * </p>
 *
 * @author 丢失的橘子
 * @since 2022-10-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_fund_history")
@ApiModel(value = "DataFundHistory对象", description = "基金历史数据")
public class DataFundHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("基金代码")
    private String code;

    @ApiModelProperty("时间")
    private LocalDate date;

    @ApiModelProperty("收益率")
    private BigDecimal earningsRate;

    @ApiModelProperty("净值")
    private BigDecimal netValue;
}
