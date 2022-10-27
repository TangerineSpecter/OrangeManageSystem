package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IDataTradeLogicService extends BaseService<TradeLogicQueryObject, DataTradeLogic> {

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
