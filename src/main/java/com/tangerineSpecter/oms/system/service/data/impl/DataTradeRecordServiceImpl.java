package com.tangerinespecter.oms.system.service.data.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.data.IDateTradeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class DataTradeRecordServiceImpl implements IDateTradeRecordService {

    /**
     * 美元汇率
     */
    private static final Double USD_EXCHANGE_RATE = 7.2;

    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;


    @Override
    public ServiceResult queryForPage(TradeRecordQueryObject qo) {
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
        PageInfo<DataTradeRecord> tradeRecordPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, tradeRecordPageInfo.getTotal());
    }

    @Override
    public ServiceResult init() {
        List<Integer> types = TradeRecordTypeEnum.getTypes();
        types.forEach(this::handlerTradeData);
        return ServiceResult.success();
    }

    /**
     * 根据类型处理数据
     *
     * @param type 交易类型
     */
    private void handlerTradeData(Integer type) {
        QueryWrapper<DataTradeRecord> queryWrapper = new QueryWrapper<DataTradeRecord>().eq("type", type);
        List<DataTradeRecord> datas = dataTradeRecordMapper.selectList(queryWrapper);
        CompletableFuture.runAsync(() -> datas.forEach(this::handlerSingleTradeData));
    }

    /**
     * 根据类型处理数据
     *
     * @param id 交易数据id
     */
    private void handlerSingleTradeData(Long id) {
        if (id == null) {
            return;
        }
        DataTradeRecord data = dataTradeRecordMapper.selectById(id);
        handlerSingleTradeData(data);
    }

    private void handlerSingleTradeData(DataTradeRecord data) {
        if (data == null) {
            log.info("交易数据不存在");
            return;
        }
        QueryWrapper<DataTradeRecord> queryWrapper = new QueryWrapper<DataTradeRecord>().eq("type", data.getType());
        int totalDay = dataTradeRecordMapper.selectCount(queryWrapper);
        //获胜次数
        int winCount = dataTradeRecordMapper.getTradeWinCountByTypeAndDate(data.getType(), data.getDate());
        int incomeValue = data.getEndMoney() - data.getStartMoney();
        data.setIncomeValue(Convert.toBigDecimal(incomeValue));
        data.setIncomeRate(NumberUtil.div(data.getIncomeValue(), data.getStartMoney(), 5));
        data.setWinRate(new BigDecimal(NumberUtil.div(winCount, totalDay, 5)));
        dataTradeRecordMapper.updateById(data);
    }

    @Override
    @Transactional
    public ServiceResult excelInfo(MultipartFile file) {
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
                        .date(date).endMoney(Convert.toInt(NumberUtil.mul(endMoney, 100))).createTime(System.currentTimeMillis())
                        .uid(SystemUtils.getSystemUserId())
                        .type(type).build();
                dataTradeRecordMapper.insert(tradeRecord);
            }
        } catch (Exception e) {
            log.error("数据导入异常：", e);
        }
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertInfo(TradeRecordInfoVo vo) {
        if (vo.getType().equals(TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getType())) {
            vo.setStartMoney(vo.getStartMoney() * USD_EXCHANGE_RATE);
            vo.setEndMoney(vo.getEndMoney() * USD_EXCHANGE_RATE);
        }
        DataTradeRecord tradeRecord = DataTradeRecord.builder().startMoney(Convert.toInt(vo.getStartMoney() * 100))
                .endMoney(Convert.toInt(vo.getEndMoney() * 100)).type(vo.getType())
                .uid(SystemUtils.getSystemUserId())
                .createTime(System.currentTimeMillis())
                .date(vo.getDate()).build();
        dataTradeRecordMapper.insert(tradeRecord);
        Long id = tradeRecord.getId();
        handlerSingleTradeData(id);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateInfo(TradeRecordInfoVo vo) {
        if (vo.getId() == null) {
            return ServiceResult.paramError();
        }
        DataTradeRecord dataTradeRecord = dataTradeRecordMapper.selectById(vo.getId());
        if (dataTradeRecord == null) {
            return ServiceResult.error(RetCode.TRADE_RECORD_NOT_EXIST);
        }
        if (vo.getType().equals(TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getType())) {
            vo.setStartMoney(vo.getStartMoney() * USD_EXCHANGE_RATE);
            vo.setEndMoney(vo.getEndMoney() * USD_EXCHANGE_RATE);
        }
        dataTradeRecord.setStartMoney(Convert.toInt(vo.getStartMoney() * 100));
        dataTradeRecord.setEndMoney(Convert.toInt(vo.getEndMoney() * 100));
        dataTradeRecord.setDate(vo.getDate());
        dataTradeRecord.setType(vo.getType());
        dataTradeRecordMapper.updateById(dataTradeRecord);
        handlerSingleTradeData(dataTradeRecord.getId());
        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        dataTradeRecordMapper.deleteById(id);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult detailInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        DataTradeRecord tradeRecord = dataTradeRecordMapper.selectById(id);
        return ServiceResult.success(tradeRecord);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        String path = "/Users/zhouliangjun/Downloads/trade.xlsx";
//        File file = new File(path);
//        if (file.exists()) {
//            System.out.println("file not exist!");
//        }
//        ExcelReader reader = ExcelUtil.getReader(new FileInputStream(file));
//        List<List<Object>> read = reader.read();
//        for (List<Object> objects : read) {
//            System.out.println(objects);
//        }
        System.out.println(TradeRecordTypeEnum.FOREIGN_EXCHANGE_TYPE.getType());
    }
}
