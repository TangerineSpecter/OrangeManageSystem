package com.tangerinespecter.oms.system.service.data.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.convert.data.TradeConvert;
import com.tangerinespecter.oms.system.domain.entity.DataExchangeRate;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.mapper.DataExchangeRateMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataTradeRecordServiceImpl implements IDateTradeRecordService {

    /**
     * 美元汇率
     */
    private static final Double USD_EXCHANGE_RATE = 7.2;

    private final DataTradeRecordMapper dataTradeRecordMapper;

    private final DataExchangeRateMapper dataExchangeRateMapper;

    /**
     * 汇率缓存
     * key：code
     * value：price
     */
    public LoadingCache<String, BigDecimal> rateCache = CacheBuilder.newBuilder()
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //最大缓存容量
            .maximumSize(20)
            //1小时后刷新缓存的数据
            .refreshAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, BigDecimal>() {
                //读取不到值，自动进行加载
                @Override
                public BigDecimal load(@NotNull String key) throws Exception {
                    Thread.sleep(1000);
                    log.info("refresh trade rate...");
                    DataExchangeRate dataExchangeRate = dataExchangeRateMapper.selectLastOneByCode(key);
                    return dataExchangeRate.getPrice();
                }
            });


    @Override
    public PageInfo<DataTradeRecord> queryForPage(TradeRecordQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataTradeRecord> pageList = dataTradeRecordMapper.queryForPage(qo);
        for (DataTradeRecord dto : pageList) {
            if (!CommonConstant.Number.COMMON_NUMBER_ZERO.equals(dto.getStartMoney())) {
                BigDecimal incomeRate = NumberUtil.div(dto.getIncomeValue(), dto.getStartMoney(), 5);
                dto.setIncomeRate(incomeRate);
            }
            BigDecimal incomeValue = NumberUtil.div(dto.getIncomeValue(), 100, 2);
            dto.setIncomeValue(incomeValue);
        }
        return new PageInfo<>(pageList);
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
            List<DataTradeRecord> dataTradeRecords = dataTradeRecordMapper.selectListByType(type, UserContext.getUid());
            CollUtils.forEach(dataTradeRecords, this::handlerSingleTradeData);
            //计算前后日期金额差值
            IntStream.range(1, dataTradeRecords.size()).forEach(index -> {
                DataTradeRecord lastData = dataTradeRecords.get(index);
                DataTradeRecord currentData = dataTradeRecords.get(index - 1);
                //前后金额差值
                int subtractMoney = lastData.getStartMoney() - currentData.getEndMoney();
                if (subtractMoney > 0) {
                    lastData.setDeposit(subtractMoney);
                } else if (subtractMoney < 0) {
                    lastData.setWithdrawal(Math.abs(subtractMoney));
                } else {
                    lastData.setDeposit(0);
                    lastData.setWithdrawal(0);
                }
                dataTradeRecordMapper.updateById(lastData);
            });
        });
    }

    /**
     * 根据id处理单条交易数据
     *
     * @param id 交易数据id
     */
    private void handlerSingleTradeData(Long id) {
        this.handlerSingleTradeData(dataTradeRecordMapper.selectById(id));
    }

    private void handlerSingleTradeData(DataTradeRecord data) {
        if (data == null) {
            log.info("交易数据不存在");
            return;
        }
        int totalCount = dataTradeRecordMapper.selectCountLeDateByType(data.getType(), data.getDate(), UserContext.getUid());
        //获胜次数
        int winCount = dataTradeRecordMapper.getTradeWinCountByTypeAndDate(data.getType(), data.getDate(), UserContext.getUid());
        int incomeValue = data.getEndMoney() - data.getStartMoney();
        data.setIncomeValue(Convert.toBigDecimal(incomeValue));
        data.setIncomeRate(NumberUtil.div(data.getIncomeValue(), data.getStartMoney(), 5));
        data.setWinRate(Convert.toBigDecimal(NumberUtil.div(winCount, totalCount, 5)));
        dataTradeRecordMapper.updateById(data);
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
            Integer endMoney = dataTradeRecordMapper.selectLastEndMoneyByType(vo.getType(), UserContext.getUid());
            tradeRecord.setStartMoney(endMoney);
        }
        dataTradeRecordMapper.insert(tradeRecord);
        handlerSingleTradeData(tradeRecord.getId());
    }

    @Override
    public void updateInfo(TradeRecordInfoVo vo) {
        DataTradeRecord dataTradeRecord = TradeConvert.INSTANCE.convert(vo);
        int i = dataTradeRecordMapper.updateById(dataTradeRecord);
        Assert.isTrue(i > 0, () -> new BusinessException(RetCode.TRADE_RECORD_NOT_EXIST));
        handlerSingleTradeData(dataTradeRecord.getId());
    }

    @Override
    public void deleteInfo(Long id) {
        Assert.notNull(id, () -> new BusinessException(RetCode.PARAM_ERROR));
        dataTradeRecordMapper.deleteById(id);
    }

    @Override
    public DataTradeRecord detailInfo(Long id) {
        Assert.notNull(id, () -> new BusinessException(RetCode.PARAM_ERROR));
        return dataTradeRecordMapper.selectById(id);
    }
}
