package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;

public interface IDataTradeLogicService {

    ServiceResult queryForPage(TradeLogicQueryObject qo);

    ServiceResult insertInfo(AddTradeLogicVo vo);

    ServiceResult updateInfo(EditTradeLogicVo vo);

    ServiceResult deleteInfo(Long id);
}
