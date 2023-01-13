package com.tangerinespecter.oms.system.domain.dto.statis;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.common.utils.CollUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 丢失的橘子
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("基金分析结果")
public class FundAnalysisInfoDto implements Serializable {

    @ApiModelProperty("时间")
    private List<String> date = CollUtil.newArrayList();

    @ApiModelProperty("收益")
    private List<BigDecimal> income = CollUtil.newArrayList();

    @ApiModelProperty("收益率")
    private List<BigDecimal> rate = CollUtil.newArrayList();

    @ApiModelProperty("最大收益")
    private BigDecimal maxIncome = BigDecimal.ZERO;

    @ApiModelProperty("最大回撤率")
    private BigDecimal maxLoss = BigDecimal.ZERO;

    @ApiModelProperty("最大衰落天数")
    private Integer maxLossDay = 0;

    @ApiModelProperty("基金交易明细列表")
    private List<AnalysisInfo> list = CollUtil.newArrayList();

    /**
     * 初始化统计数据
     */
    public void initStatisData() {
        this.date = CollUtils.convertList(this.list, FundAnalysisInfoDto.AnalysisInfo::getDate);
        this.rate = CollUtils.convertList(this.list, FundAnalysisInfoDto.AnalysisInfo::getTotalRate);
        this.income = CollUtils.convertList(this.list, FundAnalysisInfoDto.AnalysisInfo::getTotalIncome);
    }

    /**
     * 面板计算
     *
     * @param totalIncome 累计收益
     * @param rate        当前收益率
     * @param lossDay     当前浮亏天数
     */
    public void calPanelInfo(BigDecimal totalIncome, BigDecimal rate, Integer lossDay) {
        this.maxIncome = totalIncome.compareTo(this.maxIncome) > 0 ? totalIncome : this.maxIncome;
        this.maxLoss = rate.compareTo(this.maxLoss) < 0 ? rate : this.maxLoss;
        this.maxLossDay = lossDay > this.maxLossDay ? lossDay : this.maxLossDay;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnalysisInfo implements Serializable {

        @ApiModelProperty("时间")
        private String date;
        @ApiModelProperty("当前收益")
        private BigDecimal income = BigDecimal.ZERO;
        @ApiModelProperty("当前累计收益")
        private BigDecimal totalIncome = BigDecimal.ZERO;
        @ApiModelProperty("当前收益率")
        private BigDecimal rate = BigDecimal.ZERO;
        @ApiModelProperty("当前累计收益率")
        private BigDecimal totalRate = BigDecimal.ZERO;
        @ApiModelProperty("操作，0：无操作；1：买入；2：卖出")
        private Integer operation = 0;
        @ApiModelProperty("成交份额")
        private BigDecimal number;

        public AnalysisInfo(String date) {
            this.date = date;
        }
    }

}
