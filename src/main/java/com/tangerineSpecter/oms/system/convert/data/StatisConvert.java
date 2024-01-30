package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.entity.StatisTradeRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

/**
 * 统计相关转换
 *
 * @author 丢失的橘子
 */
@Mapper
public interface StatisConvert extends BaseConvert {

    StatisConvert INSTANCE = Mappers.getMapper(StatisConvert.class);

    @Mappings({
        @Mapping(target = "todayIncomeValue", expression = "java(calFen2Yuan(statisData.getIncomeValue()))"),
        @Mapping(target = "weekIncomeValue", expression = "java(calFen2Yuan(statisData.getWeekIncomeValue()))"),
        @Mapping(target = "monthIncomeValue", expression = "java(calFen2Yuan(statisData.getMonthIncomeValue()))"),
        @Mapping(target = "yearIncomeValue", expression = "java(calFen2Yuan(statisData.getYearIncomeValue()))"),
        @Mapping(target = "totalDeposit", expression = "java(calFen2Yuan(statisData.getTotalDeposit()))"),
        @Mapping(target = "totalWithdrawal", expression = "java(calFen2Yuan(statisData.getTotalWithdrawal()))"),
        @Mapping(target = "totalCash", expression = "java(calFen2Yuan(statisData.getTotalWithdrawal() - statisData.getTotalDeposit()))"),
        @Mapping(target = "totalIncomeValue", expression = "java(calFen2Yuan(statisData.getTotalIncomeValue()))"),
        @Mapping(target = "today", source = "date"),
        @Mapping(target = "todayIncomeRate", source = "incomeRate"),
    })
    StatisticsInfo convert(StatisTradeRecord statisData);

    /**
     * rmb单位：分转元
     *
     * @param value 值
     * @return 计算结果
     */
    @Named("fen2yuan")
    default BigDecimal calFen2Yuan(Integer value) {
        return NumChainCal.startOf(value).div(100).getBigDecimal(2);
    }
}
