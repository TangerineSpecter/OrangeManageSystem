package com.tangerinespecter.oms.system.service.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;

public interface IDataTradeLogicService {

    /**
     * 交易逻辑列表
     *
     * @param qo 查询参数
     * @return 分页结果
     */
    PageInfo<DataTradeLogic> queryForPage(TradeLogicQueryObject qo);

    /**
     * 添加交易数据
     *
     * @param param 请求参数
     */
    void insertInfo(AddTradeLogicVo param);

    /**
     * 编辑交易数据
     *
     * @param param 请求参数
     */
    void updateInfo(EditTradeLogicVo param);

    /**
     * 删除交易逻辑
     *
     * @param id 数据id
     */
    void deleteInfo(Long id);
}
