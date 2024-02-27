package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 交易记录统计表
 * </p>
 *
 * @author 丢失的橘子
 * @since 2024-01-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("statis_trade_record")
@ApiModel(value = "StatisTradeRecord对象", description = "交易记录统计表")
public class StatisTradeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("统计时间，yyyy-MM-dd")
    private String date;

    @ApiModelProperty("用户id")
    private String uid;

    @ApiModelProperty("当前资金（单位分），（按照当日汇率合并）")
    private Integer money = 0;

    @ApiModelProperty("当日收益（单位分），（按照当日汇率合并）")
    private Integer incomeValue = 0;

    @ApiModelProperty("当日收益率（百分比）")
    private BigDecimal incomeRate = BigDecimal.ZERO;

    @ApiModelProperty("周数")
    private Integer week;

    @ApiModelProperty("本周收益（单位分），（按照当日汇率合并）")
    private Integer weekIncomeValue = 0;

    @ApiModelProperty("本周收益率（百分比）")
    private BigDecimal weekIncomeRate = BigDecimal.ZERO;

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("本月收益（单位分），（按照当日汇率合并）")
    private Integer monthIncomeValue = 0;

    @ApiModelProperty("本月收益率（百分比）")
    private BigDecimal monthIncomeRate = BigDecimal.ZERO;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("本年收益（单位分），（按照当日汇率合并）")
    private Integer yearIncomeValue = 0;

    @ApiModelProperty("本年收益率（百分比）")
    private BigDecimal yearIncomeRate = BigDecimal.ZERO;

    @ApiModelProperty("累计收益（单位分），（按照当日汇率合并）")
    private Integer totalIncomeValue = 0;

    @ApiModelProperty("当日转入（单位分），（按照汇率合并）")
    private Integer deposit;

    @ApiModelProperty("当日转出（单位分），（按照汇率合并）")
    private Integer withdrawal;

    @ApiModelProperty("累计转入（单位分），（按照汇率合并）")
    private Integer totalDeposit = 0;

    @ApiModelProperty("累计转出（单位分），（按照汇率合并）")
    private Integer totalWithdrawal = 0;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
