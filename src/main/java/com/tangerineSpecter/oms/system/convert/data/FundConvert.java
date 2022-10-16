package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 丢失的橘子
 */
@Mapper
@SuppressWarnings("all")
public interface FundConvert extends BaseConvert {

    FundConvert INSTANCE = Mappers.getMapper(FundConvert.class);

//    List<DataFundHistory> convert(String code, List<FundHistoryData> resHistoryDatas);

    @Mappings({
            @Mapping(target = "code", source = "code"),
            @Mapping(target = "date", expression = "java(data.getDate())"),
            @Mapping(target = "earningsRate", expression = "java(data.getPercentage())"),
            @Mapping(target = "netValue", expression = "java(data.getNav())")
    })
    DataFundHistory convert(String code, FundHistoryData data);
}
