package com.tangerinespecter.oms.system.service.table.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.config.FundApiConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.job.model.FundHistoryData;
import com.tangerinespecter.oms.job.model.FundHistoryResponse;
import com.tangerinespecter.oms.job.schedule.SendMsgBot;
import com.tangerinespecter.oms.system.convert.data.FundConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFund;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import com.tangerinespecter.oms.system.mapper.DataFundHistoryMapper;
import com.tangerinespecter.oms.system.mapper.DataFundMapper;
import com.tangerinespecter.oms.system.service.table.IDataFundHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataFundHistoryServiceImpl implements IDataFundHistoryService {

    private final DataFundMapper dataFundMapper;
    private final DataFundHistoryMapper dataFundHistoryMapper;
    private final SendMsgBot botService;
    private final SqlSessionFactory sqlSessionFactory;

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
        CollUtils.forEach(fundCodes, fundCode -> executorService.execute(() -> {
            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            try {
                final long startTime = System.currentTimeMillis();
                int page = 1;
                DataFundHistory existFundHistory = dataFundHistoryMapper.selectOneByCode(fundCode);
                //如果存在数据，则按照数据维护，否则从2000年开始处理
                LocalDateTime existDate = existFundHistory == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : existFundHistory.getDate();
                //总新增入库数据
                List<DataFundHistory> insertTotalData = CollUtil.newArrayList();

                //遍历数据直到没有新增数据
                while (true) {
                    List<DataFundHistory> fundHistory = this.getFundHistory(fundCode, page);
                    //过滤出爬取数据日期在数据库已存在日期之后的数据
                    List<DataFundHistory> insertHistoryData = CollUtils.filterList(fundHistory, data -> data.getDate()
                            .isAfter(existDate));
                    CollUtil.addAll(insertTotalData, insertHistoryData);
                    //爬取数据为空 or 增长数据非实际查询数据，说明存在已入库数据，则终止循环
                    if (CollUtil.isEmpty(fundHistory) || !Objects.equals(CollUtil.size(insertHistoryData), CollUtil.size(fundHistory))) {
                        break;
                    }
                    log.info("基金[{}]当前页[{}]无旧数据，继续进行数据获取", fundCode, page);
                    page++;
                }
                log.info("基金[{}]，新增历史数据：[{}]条", fundCode, insertTotalData.size());
                CollUtils.forEach(insertTotalData, data -> {
                    DataFundHistoryMapper mapper = sqlSession.getMapper(DataFundHistoryMapper.class);
                    mapper.insert(data);
                });
                sqlSession.commit();
                this.handleFundSplitRate(fundCode);
                log.info("基金[{}]，数据处理完毕，耗时：{}ms", fundCode, System.currentTimeMillis() - startTime);
            } catch (Exception e) {
                sqlSession.rollback();
                log.error("基金[{}]数据处理异常，异常信息：[{}]，" + e, fundCode, e.getMessage());
                botService.sendErrorMsg(RetCode.FUND_DATA_ERROR, e.getMessage());
            } finally {
                sqlSession.close();
            }
        }));
    }

    @Override
    public void initAllFundHistory() {
        List<DataFund> dataFunds = dataFundMapper.selectAllList();
        this.initFundHistory(CollUtils.convertList(dataFunds, DataFund::getCode));
    }

    /**
     * 获取基金历史净值
     */
    public List<DataFundHistory> getFundHistory(String code, int page) {
        String requestUrl = CharSequenceUtil.format(FundApiConfig.DJ_FUND_HISTORY_API, code, page, DEFAULT_FUND_HISTORY_SIZE);
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
        final Elements elements = document.getElementsByClass("w782 comm fhxq").get(0).getElementsByTag("tbody").get(0)
                .getElementsByTag("tr");
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
