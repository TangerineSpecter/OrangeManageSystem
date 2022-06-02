package com.tangerinespecter.oms.system.service.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface IDateTradeRecordService {

    PageInfo<DataTradeRecord> queryForPage(TradeRecordQueryObject qo);

    void init();

    void excelInfo(MultipartFile file);

    void insertInfo(TradeRecordInfoVo vo);

    void updateInfo(TradeRecordInfoVo vo);

    void deleteInfo(Long id);

    DataTradeRecord detailInfo(Long id);

}
