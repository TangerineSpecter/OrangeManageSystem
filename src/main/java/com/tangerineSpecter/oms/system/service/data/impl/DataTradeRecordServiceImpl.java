package com.tangerinespecter.oms.system.service.data.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.convert.data.TradeConvert;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDataTradeRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataTradeRecordServiceImpl implements IDataTradeRecordService {

    private final DataTradeRecordMapper dataTradeRecordMapper;
    private final DataExchangeRateMapper dataExchangeRateMapper;

    @Override
    public List<DataTradeRecord> list(TradeRecordQueryObject qo) {
        return dataTradeRecordMapper.queryForPage(qo);
    }

    @Override
    public void init() {
        CollUtils.forEach(TradeRecordTypeEnum.getTypes(), this::handlerTradeData);
    }

    /**
     * 根据类型处理数据
     *
     * @param type 交易类型
     */
    private void handlerTradeData(Integer type) {
        CompletableFuture.runAsync(() -> {
            List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.selectListByType(type);
            CollUtils.forEach(dataTradeRecords, data -> dataTradeRecordMapper.updateById(this.handlerSingleTradeData(data)));
            this.refreshTradeDifference(dataTradeRecords);
        });
    }

    /**
     * 刷新账户出入金差额
     *
     * @param dataTradeRecords 资金数据
     */
    private void refreshTradeDifference(List<DataTradeRecord> dataTradeRecords) {
        //计算前后日期金额差值
        IntStream.range(0, dataTradeRecords.size()).forEach(index -> {
            DataTradeRecord currentData = CollUtil.get(dataTradeRecords, index);
            DataTradeRecord prevData = CollUtil.get(dataTradeRecords, index - 1 < 0 ? Integer.MAX_VALUE : index - 1);
            dataTradeRecordMapper.updateById(currentData.initData(prevData));
        });
    }

    /**
     * 根据id处理单条交易数据
     *
     * @param id 交易数据id
     */
    private void handlerSingleTradeData(Long id) {
        DataTradeRecord data = this.handlerSingleTradeData(dataTradeRecordMapper.selectById(id));
        DataTradeRecord prevData = dataTradeRecordMapper.selectLastOneBeforeDate(data.getType(), data.getDate());
        dataTradeRecordMapper.updateById(data.initData(prevData));
    }

    /**
     * 计算单笔交易数据
     *
     * @param data 交易数据
     * @return 返回处理过的数据
     */
    private DataTradeRecord handlerSingleTradeData(DataTradeRecord data) {
        if (data == null) {
            return new DataTradeRecord();
        }
        //总交易次数
        long totalCount = dataTradeRecordMapper.selectCountLeDateByType(data.getType(), data.getDate());
        //获胜次数
        int winCount = dataTradeRecordMapper.getTradeWinCountByTypeAndDate(data.getType(), data.getDate(), UserContext.getUid());
        //胜率 = 获胜次数 / 总交易次数
        data.setWinRate(NumChainCal.startOf(winCount).div(totalCount, 5).getBigDecimal());
        //收益值 = 收盘资金 - 开盘资金
        data.setIncomeValue(NumChainCal.startOf(data.getEndMoney()).sub(data.getStartMoney()).getInteger());
        //根据原始本金计算收益率 = 收益值 / 开盘资金
        data.setIncomeRate(NumChainCal.startOf(data.getIncomeValue()).div(data.getStartMoney(), 5).getBigDecimal());
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excelInfo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<List<Object>> read = reader.read();
            boolean jumpOne = true;
            for (List<Object> data : read) {
                if (jumpOne) {
                    jumpOne = false;
                    continue;
                }
                String date = Convert.toStr(data.get(0));
                BigDecimal startMoney = Convert.toBigDecimal(data.get(1));
                BigDecimal endMoney = Convert.toBigDecimal(data.get(2));
                Integer type = Convert.toInt(data.get(3));
                DataTradeRecord tradeRecord = DataTradeRecord.builder().startMoney(Convert.toInt(NumberUtil.mul(startMoney, 100)))
                        .date(date).endMoney(Convert.toInt(NumberUtil.mul(endMoney, 100)))
                        .type(type).build();
                dataTradeRecordMapper.insert(tradeRecord);
            }
        } catch (Exception e) {
            log.error("数据导入异常：", e);
        }
    }

    @Override
    public void insertInfo(TradeRecordInfoVo vo) {
        DataTradeRecord tradeRecord = TradeConvert.INSTANCE.convert(vo);
        //无数据则采用上一次
        if (tradeRecord.getStartMoney() == null) {
            Integer endMoney = dataTradeRecordMapper.selectLastEndMoneyByType(vo.getType(), vo.getDate());
            tradeRecord.setStartMoney(endMoney);
        }
        //依据出入金额计算起始资金
        tradeRecord.setStartMoney(NumChainCal.startOf(tradeRecord.getStartMoney())
                .add(tradeRecord.getDeposit()).sub(tradeRecord.getWithdrawal()).getInteger());
        dataTradeRecordMapper.insert(tradeRecord);
        this.handlerSingleTradeData(tradeRecord.getId());
    }

    @Override
    public void updateInfo(TradeRecordInfoVo vo) {
        DataTradeRecord tradeRecord = TradeConvert.INSTANCE.convert(vo);
        //无数据则采用上一次
        if (tradeRecord.getStartMoney() == null) {
            Integer endMoney = dataTradeRecordMapper.selectLastEndMoneyByType(vo.getType(), vo.getDate());
            tradeRecord.setStartMoney(endMoney);
        }
        int i = dataTradeRecordMapper.updateById(tradeRecord);
        Assert.isTrue(i > 0, () -> new BusinessException(RetCode.TRADE_RECORD_NOT_EXIST));
        this.handlerSingleTradeData(tradeRecord.getId());
    }

    @Override
    public void deleteInfo(Long id) {
        dataTradeRecordMapper.deleteById(id);
    }

    @Override
    public DataTradeRecord detailInfo(Long id) {
        return dataTradeRecordMapper.selectById(id);
    }

    /**
     * 根据代码获取最近的汇率
     *
     * @param code 货币代码
     * @return 汇率
     */
    @Cacheable(cacheNames = "DATA:EXCHANGE:RATE", key = "#code")
    public BigDecimal getExchangeRateByCode(String code) {
        DataExchangeRate dataExchangeRate = dataExchangeRateMapper.selectLastOneByCode(code);
        //无数据则采用CNY比例
        if (dataExchangeRate == null) {
            return new BigDecimal(1);
        }
        return dataExchangeRate.getPrice();
    }
}
