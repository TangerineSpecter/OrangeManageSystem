package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.domain.vo.statis.FundAnalysisInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
            @Mapping(target = "date", source = "data.date"),
            @Mapping(target = "earningsRate", source = "data.percentage"),
            @Mapping(target = "netValue", source = "data.nav")
    })
    DataFundHistory convert(String code, FundHistoryData data);

    FundHistoryQueryObject convert(FundAnalysisInfoVo vo);
}
