package com.tangerinespecter.oms.job.service;

import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.dto.data.FundInitDataDto;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.service.table.IDataFundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundDataQuartzService {

    private final IDataFundService dataFundService;

    public void runData() {
        log.info("[执行基金每日数据写入任务]");
        FundInitDataDto fundInitDataDto = dataFundService.initFund();
        //处理基金历史数据
        dataFundService.initFundHistory(CollUtils.convertList(fundInitDataDto.getAllFundData(), DataFund::getCode));
        log.info("[基金数据定时任务执行完毕]");
    }

}
