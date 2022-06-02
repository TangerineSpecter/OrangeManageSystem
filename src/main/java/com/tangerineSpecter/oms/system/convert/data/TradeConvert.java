package com.tangerinespecter.oms.system.convert.data;

import cn.hutool.core.convert.Convert;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface TradeConvert {

    TradeConvert INSTANCE = Mappers.getMapper(TradeConvert.class);

    @Mappings({
            @Mapping(target = "startMoney", expression = "java(convertDbMoneyData(vo.getStartMoney()))"),
            @Mapping(target = "endMoney", expression = "java(convertDbMoneyData(vo.getEndMoney()))"),
            @Mapping(target = "deposit", expression = "java(convertDbMoneyData(vo.getDeposit()))"),
            @Mapping(target = "withdrawal", expression = "java(convertDbMoneyData(vo.getWithdrawal()))")
    })
    DataTradeRecord convert(TradeRecordInfoVo vo);

    /**
     * 转化为db data
     *
     * @param moneyData 请求的金钱数据，小数后两位
     * @return db数据
     */
    @Named("convertDbMoneyData")
    default Integer convertDbMoneyData(Double moneyData) {
        if (moneyData == null) {
            return null;
        }
        return Convert.toInt(moneyData * 100);
    }
}
