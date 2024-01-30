package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface TradeConvert extends BaseConvert {

    TradeConvert INSTANCE = Mappers.getMapper(TradeConvert.class);

    @Mappings({
        @Mapping(target = "endMoney", expression = "java(convertDbMoneyData(vo.getEndMoney()))"),
        @Mapping(target = "deposit", expression = "java(convertDbMoneyData(vo.getDeposit()))"),
        @Mapping(target = "withdrawal", expression = "java(convertDbMoneyData(vo.getWithdrawal()))")
    })
    DataTradeRecord convert(TradeRecordInfoVo vo);

    /**
     * 编辑参数 -> 交易逻辑model
     *
     * @param vo 编辑参数
     * @return 交易逻辑model
     */
    DataTradeLogic convert(EditTradeLogicVo vo);

    /**
     * 添加参数 -> 交易逻辑model
     *
     * @param vo 添加参数
     * @return 交易逻辑model
     */
    @Mappings({
        @Mapping(target = "uid", expression = "java(uid())")
    })
    DataTradeLogic convert(AddTradeLogicVo vo);

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
        return NumChainCal.startOf(moneyData).mul(100).getInteger();
    }
}
