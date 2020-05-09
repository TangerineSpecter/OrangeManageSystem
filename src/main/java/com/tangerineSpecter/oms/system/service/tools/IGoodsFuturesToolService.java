package com.tangerinespecter.oms.system.service.tools;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.GoodsFuturesInfoVo;

public interface IGoodsFuturesToolService {


    ServiceResult createFuturesInfo(GoodsFuturesInfoVo vo);
}
