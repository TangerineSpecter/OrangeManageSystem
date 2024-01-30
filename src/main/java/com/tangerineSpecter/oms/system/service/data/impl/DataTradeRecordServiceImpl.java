package com.tangerinespecter.oms.system.service.data.impl;

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
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 丢失的橘子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataTradeRecordServiceImpl implements IDataTradeRecordService {

    private final ITradeStatisService tradeStatisService;

    private final DataTradeRecordMapper dataTradeRecordMapper;
    private final DataExchangeRateMapper dataExchangeRateMapper;

    private final ApplicationEventPublisher publisher;

    @Override
    public List<DataTradeRecord> list(TradeRecordQueryObject qo) {
        return dataTradeRecordMapper.queryForPage(qo);
    }

    @Override
    public void init() {
//        CollUtils.forEach(TradeRecordTypeEnum.getTypes(), this::handlerTradeData);
        publisher.publishEvent("1970-01-01");
    }

    /**
     * 根据类型处理数据
     *
     * @param type 交易类型
     */
    private void handlerTradeData(Integer type) {
        CompletableFuture.runAsync(() -> {
            log.info("类型[{}]开始初始化交易记录...", type);
            List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.selectListByType(type, null, true);
            CollUtils.forEach(dataTradeRecords, this::handlerSingleTradeData);
            log.info("类型[{}]交易记录处理完毕...", type);
        });
    }

    /**
     * 填充利率
     *
     * @param currentData 当前数据
     */
    public void fillDepositRateAndWithdrawalRate(DataTradeRecord currentData) {
        //有交易记录，没有利率则使用默认
        boolean haveDeposit = currentData.getDeposit() > 0 && currentData.getDepositRate()
            .compareTo(BigDecimal.ZERO) <= 0;
        //无记录，则采用默认时间就近利率，默认CNY为1
        if (haveDeposit) {
            DataExchangeRate exchangeRate = dataExchangeRateMapper.selectOneByLastRecordTime(currentData.getDate(), currentData.getCurrency());
            BigDecimal depositRate = exchangeRate == null ? BigDecimal.ONE : NumChainCal
                .startOf(exchangeRate.getPrice()).div(100).getBigDecimal();
            currentData.setDepositRate(depositRate);
        }
        boolean haveWithdrawal = currentData.getWithdrawal() > 0 && currentData.getWithdrawalRate()
            .compareTo(BigDecimal.ZERO) <= 0;
        if (haveWithdrawal) {
            DataExchangeRate exchangeRate = dataExchangeRateMapper.selectOneByLastRecordTime(currentData.getDate(), currentData.getCurrency());
            BigDecimal withdrawalRate = exchangeRate == null ? BigDecimal.ONE : NumChainCal
                .startOf(exchangeRate.getPrice()).div(100).getBigDecimal();
            currentData.setWithdrawalRate(withdrawalRate);
        }
    }

    /**
     * 根据id处理单条交易数据
     *
     * @param id 交易数据id
     */
    private void handlerSingleTradeData(Long id) {
        this.handlerSingleTradeData(dataTradeRecordMapper.selectById(id));
    }

    /**
     * 根据记录信息处理更新单条交易数据
     *
     * @param todayData 今日交易数据信息
     */
    private void handlerSingleTradeData(DataTradeRecord todayData) {
        dataTradeRecordMapper.updateById(this.fillSingleTradeData(todayData));
    }

    @Override
    public DataTradeRecord fillSingleTradeData(DataTradeRecord data) {
        if (data == null) {
            return new DataTradeRecord();
        }
        log.info("开始处理时间[{}]，类型[{}]数据", data.getDate(), data.getType());
        DataTradeRecord prevData = dataTradeRecordMapper.selectLastOneBeforeDate(data.getType(), data.getDate());
        //计算起始资金
        Integer lastDayEndMoney = prevData == null ? 0 : prevData.getEndMoney();
        this.calStartMoney(data, lastDayEndMoney);
        //收益值 = 收盘资金 - 开盘资金
        int incomeValue = NumChainCal.startOf(data.getEndMoney()).sub(data.getStartMoney()).getInteger();
        data.setIncomeValue(incomeValue);
        //总交易次数
        long totalCount = dataTradeRecordMapper.selectCountLeDateByType(data.getType(), data.getDate());
        //获胜次数 = 历史正收益次数
        int winCount = dataTradeRecordMapper.getTradeWinCountByTypeAndDate(data.getType(), data.getDate(), UserContext.getUid());
        //胜率 = 获胜次数 / 总交易次数
        data.setWinRate(NumChainCal.startOf(winCount).div(totalCount, 5).getBigDecimal());
        //根据原始本金计算收益率 = 收益值 / 开盘资金
        data.setIncomeRate(NumChainCal.startOf(data.getIncomeValue()).div(data.getStartMoney(), 5).getBigDecimal());
        //计算累计收益
        int totalIncomeValue = prevData == null ? 0 : prevData.getTotalIncomeValue();
        data.setTotalIncomeValue(NumChainCal.startOf(totalIncomeValue).add(incomeValue).getInteger());
        //计算出入金利率
        this.fillDepositRateAndWithdrawalRate(data);
        log.info("时间[{}]，类型[{}]数据处理完毕", data.getDate(), data.getType());
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
                DataTradeRecord tradeRecord = DataTradeRecord.builder()
                    .startMoney(Convert.toInt(NumberUtil.mul(startMoney, 100))).date(date)
                    .endMoney(Convert.toInt(NumberUtil.mul(endMoney, 100))).type(type).build();
                dataTradeRecordMapper.insert(tradeRecord);
            }
        } catch (Exception e) {
            log.error("数据导入异常：", e);
        }
    }

    @Override
    public void insertInfo(TradeRecordInfoVo vo) {
        try {
            //记录入库
            DataTradeRecord tradeRecord = TradeConvert.INSTANCE.convert(vo);
            dataTradeRecordMapper.insert(this.fillSingleTradeData(tradeRecord));
            //同步统计以及数据填充
            publisher.publishEvent(tradeRecord.getDate());
        } catch (DuplicateKeyException e) {
            log.error("数据冲突，异常信息：{}, 异常：" + e, e.getMessage());
            throw new BusinessException(RetCode.TODAY_TRADE_RECORD_EXIST);
        } catch (Exception e) {
            log.error("数据异常，异常信息：{}, 异常：" + e, e.getMessage());
            throw new BusinessException(RetCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void updateInfo(TradeRecordInfoVo vo) {
        try {
            DataTradeRecord tradeRecord = TradeConvert.INSTANCE.convert(vo);
            int i = dataTradeRecordMapper.updateByUid(this.fillSingleTradeData(tradeRecord));
            Assert.isTrue(i > 0, () -> new BusinessException(RetCode.TRADE_RECORD_NOT_EXIST));
            //同步统计以及数据填充
            publisher.publishEvent(tradeRecord.getDate());
        } catch (DuplicateKeyException e) {
            log.error("数据冲突，异常信息：{}, 异常：" + e, e.getMessage());
            throw new BusinessException(RetCode.TODAY_TRADE_RECORD_EXIST);
        } catch (Exception e) {
            log.error("数据异常，异常信息：{}, 异常：" + e, e.getMessage());
            throw new BusinessException(RetCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteInfo(Long id) {
        DataTradeRecord tradeRecord = dataTradeRecordMapper.selectById(id);
        Assert.isTrue(tradeRecord != null, () -> new BusinessException(RetCode.TRADE_RECORD_NOT_EXIST));
        dataTradeRecordMapper.deleteById(tradeRecord);
        tradeStatisService.deleteRefreshByDate(tradeRecord.getDate());
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

    /**
     * 计算设置初始资金
     *
     * @param tradeRecord     记录数据
     * @param lastDayEndMoney 上一天结余资金
     */
    private void calStartMoney(DataTradeRecord tradeRecord, Integer lastDayEndMoney) {
        //依据出入金额计算，今日起始资金 = 上一天结余 + 入金 - 出金
        int startMoney = NumChainCal.startOf(lastDayEndMoney).add(tradeRecord.getDeposit())
            .sub(tradeRecord.getWithdrawal()).getInteger();
        tradeRecord.setStartMoney(startMoney);
    }
}
