package com.tangerinespecter.oms.system.service.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeRecordQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.vo.data.TradeRecordInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface IDateTradeRecordService {

    /**
     * 交易记录列表
     *
     * @param qo 查询参数
     * @return 分页结果
     */
    PageInfo<DataTradeRecord> queryForPage(TradeRecordQueryObject qo);

    /**
     * 交易数据初始化
     */
    void init();

    /**
     * excel导入数据
     *
     * @param file 导入文件流
     */
    void excelInfo(MultipartFile file);

    /**
     * 添加交易数据
     *
     * @param vo 添加参数
     */
    void insertInfo(TradeRecordInfoVo vo);

    /**
     * 编辑交易数据
     *
     * @param vo 编辑参数
     */
    void updateInfo(TradeRecordInfoVo vo);

    /**
     * 删除交易数据
     *
     * @param id 数据id
     */
    void deleteInfo(Long id);

    /**
     * 交易数据详情
     *
     * @param id 数据id
     * @return 详情数据
     */
    DataTradeRecord detailInfo(Long id);

}
