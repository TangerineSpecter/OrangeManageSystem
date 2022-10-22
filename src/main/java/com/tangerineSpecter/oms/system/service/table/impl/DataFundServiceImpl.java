package com.tangerinespecter.oms.system.service.table.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.FundApiConfig;
import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.AllFundsResponse;
import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.job.model.FundHistoryResponse;
import com.tangerinespecter.oms.system.convert.data.FundConvert;
import com.tangerinespecter.oms.system.domain.dto.data.FundInitDataDto;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.mapper.DataFundMapper;
import com.tangerinespecter.oms.system.service.table.IDataFundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataFundServiceImpl implements IDataFundService {

    private final DataFundMapper dataFundMapper;
    private final DataFundHistoryMapper dataFundHistoryMapper;

    /**
     * 基金默认历史数据条数
     */
    private static final int DEFAULT_FUND_HISTORY_SIZE = 20;

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

    @Override
    public void initFundHistory(List<String> fundCodes) {
        CollUtils.forEach(fundCodes, fundCode ->
                CompletableFuture.runAsync(() -> {
                    try {
                        List<DataFundHistory> existFundHistory = dataFundHistoryMapper.selectListByCode(fundCode);
                        List<LocalDateTime> existDate = CollUtils.convertList(existFundHistory, DataFundHistory::getDate);
                        List<DataFundHistory> fundHistory = this.getFundHistory(fundCode);
                        List<DataFundHistory> insertHistoryData = CollUtils.filterList(fundHistory, data -> !existDate.contains(data.getDate()));
                        log.info("基金[{}]，新增历史数据：[{}]条", fundCode, insertHistoryData.size());
                        if (CollUtil.isNotEmpty(insertHistoryData)) {
                            dataFundHistoryMapper.batchInsert(insertHistoryData);
                        }
                    } catch (Exception e) {
                        log.error("基金[{}]数据处理异常，异常信息：[{}]，" + e, fundCode, e.getMessage());
                    }
                }));
    }

    /**
     * 获取基金历史净值
     */
    public List<DataFundHistory> getFundHistory(String code) {
        String requestUrl = CharSequenceUtil.format(FundApiConfig.DJ_FUND_HISTORY_API, code, DEFAULT_FUND_HISTORY_SIZE);
        String result = HttpUtil.get(requestUrl);
        FundHistoryResponse response = JSON.parseObject(result, FundHistoryResponse.class);
        List<FundHistoryData> resHistoryDatas = response.getData().getItems();
        return CollUtils.convertList(resHistoryDatas, data -> FundConvert.INSTANCE.convert(code, data));
    }
}
