package com.tangerinespecter.oms.job.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.FundApiConfig;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.AllFundsResponse;
import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.job.model.FundHistoryResponse;
import com.tangerinespecter.oms.system.convert.data.FundConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.mapper.DataFundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundDataQuartzService {

    private final DataFundMapper dataFundMapper;
    private final DataFundHistoryMapper dataFundHistoryMapper;
    /**
     * 基金默认历史数据条数
     */
    private static final int DEFAULT_FUND_HISTORY_SIZE = 20;

    public void runData() {
        log.info("[执行基金每日数据写入任务]");
        List<DataFund> allFunds = getAllFunds();
        List<String> existFundCodes = CollUtils.convertList(dataFundMapper.selectAllList(), DataFund::getCode);
        Collection<DataFund> insertFundData = CollUtils.filterList(allFunds, data -> !existFundCodes.contains(data.getCode()));
        log.info("查询基金数量：[{}], 已入库基金数量：[{}]，新增基金数：[{}]", allFunds.size(), existFundCodes.size(), insertFundData.size());
        CollUtils.forEach(insertFundData, dataFundMapper::insert);
        existFundCodes.addAll(CollUtils.convertList(insertFundData, DataFund::getCode));
        //处理基金历史数据
        this.handleFundHistoryData(existFundCodes);
        log.info("[基金数据定时任务执行完毕]");
    }

    public void handleFundHistoryData(List<String> fundCodes) {
        CollUtils.forEach(fundCodes, fundCode ->
                CompletableFuture.runAsync(() -> {
                    try {
                        List<DataFundHistory> existFundHistory = dataFundHistoryMapper.selectListByCode(fundCode);
                        List<LocalDate> existDate = CollUtils.convertList(existFundHistory, DataFundHistory::getDate);
                        List<DataFundHistory> fundHistory = this.getFundHistory(fundCode);
                        List<DataFundHistory> insertHistoryData = CollUtils.filterList(fundHistory, data -> !existDate.contains(data.getDate()));
                        log.info("基金[{}]，新增历史数据：[{}]条", fundCode, insertHistoryData.size());
                        if (insertHistoryData.isEmpty()) {
                            return;
                        }
                        dataFundHistoryMapper.batchInsert(insertHistoryData);
                    } catch (Exception e) {
                        log.error("基金[{}]数据处理异常，异常信息：[{}]，" + e, fundCode, e.getMessage());
                    }
                }));
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
