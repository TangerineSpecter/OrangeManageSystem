package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsInfo implements Serializable {

    @ApiModelProperty("当天收益")
    private BigDecimal todayIncome = BigDecimal.ZERO;
    @ApiModelProperty("本周收益")
    private BigDecimal weekendIncome = BigDecimal.ZERO;
    @ApiModelProperty("当月收益")
    private BigDecimal monthIncome = BigDecimal.ZERO;
    @ApiModelProperty("当年收益")
    private BigDecimal yearIncome = BigDecimal.ZERO;
    @ApiModelProperty("当天投入资金")
    private BigDecimal todayInputMoney = BigDecimal.ZERO;
    @ApiModelProperty("本周投入资金")
    private BigDecimal weekendInputMoney = BigDecimal.ZERO;
    @ApiModelProperty("当月投入资金")
    private BigDecimal monthInputMoney = BigDecimal.ZERO;
    @ApiModelProperty("当年投入资金")
    private BigDecimal yearInputMoney = BigDecimal.ZERO;
    @ApiModelProperty("今天日期")
    private String today;
    @ApiModelProperty("本周周数")
    private Integer weekend;
    @ApiModelProperty("本月月份")
    private Integer month;
    @ApiModelProperty("本年年份")
    private Integer year;
    @ApiModelProperty("今日收益率，小数位")
    private BigDecimal todayRate = BigDecimal.ZERO;
    @ApiModelProperty("本周收益率，小数位")
    private BigDecimal weekendRate = BigDecimal.ZERO;
    @ApiModelProperty("本月收益率，小数位")
    private BigDecimal monthRate = BigDecimal.ZERO;
    @ApiModelProperty("本年收益率，小数位")
    private BigDecimal yearRate = BigDecimal.ZERO;
    @ApiModelProperty("最近30日资金曲线")
    private List<Integer> lastThirtyTotalMoney;
    @ApiModelProperty("最近30天日期")
    private List<String> lastThirtyDate;

    /**
     * 初始化数据
     *
     * @param calStatisDto 计算统计结果
     */
    public StatisticsInfo(List<RecordCalStatisDto> calStatisDto) {
        Date currentDate = new Date();
        this.year = DateUtil.year(currentDate);
        this.month = DateUtil.month(currentDate) + 1;
        this.weekend = DateUtil.weekOfYear(currentDate);
        this.today = CollUtil.getLast(calStatisDto).getDate();
        this.calIncome(calStatisDto);
    }

    /**
     * 收益计算
     *
     * @param calStatisDto 计算统计结果，时间：远 -> 近
     */
    private void calIncome(List<RecordCalStatisDto> calStatisDto) {
        //获取最后一次累计的资金
        Integer lastTotalMoney = CollUtil.getLast(calStatisDto).getTotalEndMoney();
        //遍历累加
        CollUtils.forEach(calStatisDto, calStatisData -> {
            Integer income = calStatisData.getTotalIncome();
            long date = DateUtil.parse(calStatisData.getDate(), DateFormat.getDateInstance()).getTime();
            this.yearIncome = calYearIncome(date, this.yearIncome, income);
            this.monthIncome = calMonthIncome(date, this.monthIncome, income);
            this.weekendIncome = calWeekendIncome(date, this.weekendIncome, income);
            this.todayIncome = calTodayIncome(calStatisData.getDate(), this.todayIncome, income);
            this.calInputMoney(calStatisData);
        });
        this.calIncomeRate(lastTotalMoney);
        this.lastThirtyDate = CollUtils.convertReverseLimitList(calStatisDto, RecordCalStatisDto::getDate, 15);
        this.lastThirtyTotalMoney = CollUtils.convertReverseLimitList(calStatisDto, RecordCalStatisDto::getTotalEndMoney, 15);
    }

    /**
     * 计算收益率
     *
     * @param totalMoney 当前总资金
     */
    private void calIncomeRate(Integer totalMoney) {
        //年收益
        this.yearRate = this.calIncomeRate(totalMoney, this.yearIncome, this.yearInputMoney);
        //月收益
        this.monthRate = this.calIncomeRate(totalMoney, this.monthIncome, this.monthInputMoney);
        //周收益
        this.weekendRate = this.calIncomeRate(totalMoney, this.weekendIncome, this.weekendInputMoney);
        //日收益
        this.todayRate = this.calIncomeRate(totalMoney, this.todayIncome, this.todayInputMoney);
    }

    /**
     * 收益率计算
     *
     * @param currentTotalMoney 当前总资金
     * @param income            时间内总收益
     * @param inputMoney        时间内总投入
     * @return 收益率，小数保留2位
     */
    private BigDecimal calIncomeRate(Integer currentTotalMoney, BigDecimal income, BigDecimal inputMoney) {
        //剔除投入资金核算起始资金
        Integer startMoney = NumChainCal.startOf(currentTotalMoney).sub(inputMoney).getInteger();
        //收益率 = 收益 / (当前资金 - 时间内投入资金）
        return NumChainCal.startOf(income).div(startMoney).getBigDecimal(2);
    }

    /**
     * 投入计算
     *
     * @param calStatisDto 计算统计结果
     */
    private void calInputMoney(RecordCalStatisDto calStatisDto) {
        Integer inputMoney = calStatisDto.getTotalInputMoney();
        long date = DateUtil.parse(calStatisDto.getDate(), DateFormat.getDateInstance()).getTime();
        this.yearInputMoney = calYearIncome(date, this.yearInputMoney, inputMoney);
        this.monthInputMoney = calMonthIncome(date, this.monthInputMoney, inputMoney);
        this.weekendInputMoney = calWeekendIncome(date, this.weekendIncome, inputMoney);
        this.todayInputMoney = calTodayIncome(calStatisDto.getDate(), this.todayInputMoney, inputMoney);
    }

    /**
     * 计算今年累计收益
     *
     * @param currentTime 当前时间戳
     * @param startData   累加起始数据
     * @param currentData 当前时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calYearIncome(long currentTime, BigDecimal startData, Integer currentData) {
        long thisYear = DateUtil.beginOfYear(new Date()).getTime();
        if (currentTime >= thisYear) {
            return NumChainCal.startOf(startData).add(currentData).getBigDecimal(0);
        }
        return this.yearIncome;
    }

    /**
     * 计算本月累计收益
     *
     * @param currentTime 当前时间戳
     * @param startData   累加起始数据
     * @param currentData 当前时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calMonthIncome(long currentTime, BigDecimal startData, Integer currentData) {
        long thisMonth = DateUtil.beginOfMonth(new Date()).getTime();
        if (currentTime >= thisMonth) {
            return NumChainCal.startOf(startData).add(currentData).getBigDecimal(0);
        }
        return this.monthIncome;
    }

    /**
     * 计算本周累计收益
     *
     * @param currentTime 当前时间戳
     * @param startData   累加起始数据
     * @param currentData 当前时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calWeekendIncome(long currentTime, BigDecimal startData, Integer currentData) {
        long thisWeek = DateUtil.beginOfWeek(new Date()).getTime();
        if (currentTime >= thisWeek) {
            return NumChainCal.startOf(startData).add(currentData).getBigDecimal(0);
        }
        return this.weekendIncome;
    }

    /**
     * 计算近日累计收益
     *
     * @param date        时间字符串 yyyy-MM-dd
     * @param startData   累加起始数据
     * @param currentData 当前时间对应的收益
     * @return 收益结果
     */
    private BigDecimal calTodayIncome(String date, BigDecimal startData, Integer currentData) {
        if (Objects.equals(this.today, date)) {
            return NumChainCal.startOf(startData).add(currentData).getBigDecimal(0);
        }
        return this.todayIncome;
    }
}
