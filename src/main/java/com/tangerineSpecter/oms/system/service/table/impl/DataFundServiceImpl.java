package com.tangerinespecter.oms.system.service.table.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.FundApiConfig;
import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.AllFundsResponse;
import com.tangerinespecter.oms.system.domain.dto.data.FundInitDataDto;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.mapper.DataFundMapper;
import com.tangerinespecter.oms.system.service.table.IDataFundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataFundServiceImpl implements IDataFundService {

    private final DataFundMapper dataFundMapper;

    @Override
    public List<DataFund> list(FundQueryObject qo) {
        return dataFundMapper.queryForPage(qo);
    }

    @Override
    public FundInitDataDto initFund() {
        List<DataFund> allFunds = getAllFunds();
        List<String> existFundCodes = CollUtils.convertList(dataFundMapper.selectAllList(), DataFund::getCode);
        List<DataFund> insertFundData = CollUtils.filterList(allFunds, data -> !existFundCodes.contains(data.getCode()));
        log.info("查询基金数量：[{}], 已入库基金数量：[{}]，新增基金数：[{}]", allFunds.size(), existFundCodes.size(), insertFundData.size());
        CollUtils.forEach(insertFundData, dataFundMapper::insert);
        return new FundInitDataDto(insertFundData, allFunds);
    }

    /**
     * 获取全部基金数据
     *
     * @return 全部基金数据
     */
    public List<DataFund> getAllFunds() {
        String result = HttpUtil.get(FundApiConfig.ALL_FUND_LIST_API);
        AllFundsResponse response = JSON.parseObject(result, AllFundsResponse.class);
        Assert.isTrue(response.getCode() == 200, "基金接口请求异常");
        return CollUtils.convertList(response.getData(), DataFund::convert2Model);
    }
}
