package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.domain.pojo.PieChartsInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class StatisticsInfo implements Serializable {

    @ApiModelProperty("当天收益，单位：元")
    private BigDecimal todayIncomeValue = BigDecimal.ZERO;
    @ApiModelProperty("本周收益，单位：元")
    private BigDecimal weekIncomeValue = BigDecimal.ZERO;
    @ApiModelProperty("当月收益，单位：元")
    private BigDecimal monthIncomeValue = BigDecimal.ZERO;
    @ApiModelProperty("当年收益，单位：元")
    private BigDecimal yearIncomeValue = BigDecimal.ZERO;

    @ApiModelProperty("结算当天日期")
    private String today;
    @ApiModelProperty("本周周数")
    private Integer week;
    @ApiModelProperty("本月月份")
    private Integer month;
    @ApiModelProperty("本年年份")
    private Integer year;

    @ApiModelProperty("今日收益率，小数位")
    private BigDecimal todayIncomeRate = BigDecimal.ZERO;
    @ApiModelProperty("本周收益率，小数位")
    private BigDecimal weekIncomeRate = BigDecimal.ZERO;
    @ApiModelProperty("本月收益率，小数位")
    private BigDecimal monthIncomeRate = BigDecimal.ZERO;
    @ApiModelProperty("本年收益率，小数位")
    private BigDecimal yearIncomeRate = BigDecimal.ZERO;

    @ApiModelProperty("累计资金曲线，单位：元")
    private LinkedHashMap<String, BigDecimal> totalMoneys;
    @ApiModelProperty("累计收益曲线，单位：元")
    private LinkedHashMap<String, BigDecimal> totalIncomes;

    @ApiModelProperty("累计转入资金")
    private BigDecimal totalDeposit = BigDecimal.ZERO;
    @ApiModelProperty("累计转出资金")
    private BigDecimal totalWithdrawal = BigDecimal.ZERO;
    @ApiModelProperty("累计结余")
    private BigDecimal totalCash = BigDecimal.ZERO;
    @ApiModelProperty("累计盈利")
    private BigDecimal totalIncomeValue = BigDecimal.ZERO;

    @ApiModelProperty("结算资金饼图")
    private List<PieChartsInfo> endMoneyPie;

    public StatisticsInfo() {
        this.totalMoneys = new LinkedHashMap<>();
        this.totalIncomes = new LinkedHashMap<>();
        this.endMoneyPie = new ArrayList<>();
    }
}
