package com.tangerinespecter.oms.system.statis;

import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeStatisServiceImplTest {

    @Resource
    private ITradeStatisService tradeStatisService;
    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;


    @Test
    public void test() {
        DataTradeRecord data = new DataTradeRecord();
        final DataTradeRecord dataTradeRecord = dataTradeRecordMapper.selectLastOneBeforeDate(data.getType(), data.getDate());
        System.out.println(JSON.toJSONString(dataTradeRecord));
    }
}
