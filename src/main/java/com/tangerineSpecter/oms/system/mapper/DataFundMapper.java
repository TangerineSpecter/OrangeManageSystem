package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.query.FundQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFund;

import java.util.List;

public interface DataFundMapper extends BaseMapper<DataFund> {

    /**
     * 查询全部基金数据
     *
     * @return 基金数据
     */
    default List<DataFund> selectAllList() {
        return selectList(new QueryWrapper<DataFund>()
                .eq("is_del", GlobalBoolEnum.FALSE.getValue()));
    }

    /**
     * 分页查询
     *
     * @param qo 高级查询参数
     * @return 分页结果
     */
    List<DataFund> queryForPage(FundQueryObject qo);
}
