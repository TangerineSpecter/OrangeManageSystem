package com.tangerinespecter.oms.system.service.table.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.FundApiConfig;
import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.job.model.FundHistoryResponse;
import com.tangerinespecter.oms.system.convert.data.FundConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.mapper.DataFundMapper;
import com.tangerinespecter.oms.system.service.table.IDataFundHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataFundHistoryServiceImpl implements IDataFundHistoryService {

    private final DataFundMapper dataFundMapper;
    private final DataFundHistoryMapper dataFundHistoryMapper;

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 基金默认历史数据条数
     */
    private static final int DEFAULT_FUND_HISTORY_SIZE = 20;

    @Override
    public List<DataFundHistory> list(FundHistoryQueryObject qo) {
        return dataFundHistoryMapper.queryForPage(qo);
    }

    @Override
    public void initFundHistory(List<String> fundCodes) {
        CollUtils.forEach(fundCodes, fundCode ->
                executorService.execute(() -> {
                    try {
                        List<DataFundHistory> existFundHistory = dataFundHistoryMapper.selectListByCode(fundCode);
                        List<LocalDateTime> existDate = CollUtils.convertList(existFundHistory, DataFundHistory::getDate);
                        List<DataFundHistory> fundHistory = this.getFundHistory(fundCode);
                        List<DataFundHistory> insertHistoryData = CollUtils.filterList(fundHistory, data -> !existDate.contains(data.getDate()));
                        log.info("基金[{}]，新增历史数据：[{}]条", fundCode, insertHistoryData.size());
                        if (CollUtil.isNotEmpty(insertHistoryData)) {
                            dataFundHistoryMapper.batchInsert(insertHistoryData);
                        }
                        this.handleFundSplitRate(fundCode);
                    } catch (Exception e) {
                        log.error("基金[{}]数据处理异常，异常信息：[{}]，" + e, fundCode, e.getMessage());
                    }
                })
        );
    }

    @Override
    public void initAllFundHistory() {
        List<DataFund> dataFunds = dataFundMapper.selectAllList();
        this.initFundHistory(CollUtils.convertList(dataFunds, DataFund::getCode));
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

    /**
     * 处理基金拆分比例
     *
     * @param fundCode 基金代码
     */
    public void handleFundSplitRate(String fundCode) throws IOException {
        String url = CharSequenceUtil.format("https://fundf10.eastmoney.com/fhsp_{}.html", fundCode);
        //表头列数：年份、拆分折算日、拆分类型、拆分折算比例
        final int rowCount = 4;
        Document document = Jsoup.connect(url).get();
        final Elements elements = document.getElementsByClass("w782 comm fhxq").get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr");
        CollUtils.forEach(elements, element -> {
            Elements tdElements = element.getElementsByTag("td");
            if (tdElements.size() != rowCount) {
                log.info("基金：[{}]不存在拆分记录", fundCode);
                return;
            }
            String date = CollUtil.get(tdElements, 1).text();
            Double splitRate = Convert.toDouble(CharSequenceUtil.replace(CollUtil.get(tdElements, 3).text(), "1:", ""));
            log.info("基金[{}]更新拆分记录，时间：[{}]，比例：[{}]", fundCode, date, splitRate);
            dataFundHistoryMapper.updateSplitRateByDate(date, fundCode, splitRate);
        });
    }
}
