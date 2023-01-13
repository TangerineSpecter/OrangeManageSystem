package com.tangerinespecter.oms.system.service.statis;

import com.tangerinespecter.oms.system.domain.dto.statis.FundAnalysisInfoDto;
import com.tangerinespecter.oms.system.domain.vo.statis.FundAnalysisInfoVo;

public interface IFundAnalysisService {

    /**
     * 基金分析
     *
     * @param vo 分析参数
     */
    FundAnalysisInfoDto analysis(FundAnalysisInfoVo vo);
}
