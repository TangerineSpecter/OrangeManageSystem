package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.FundHistoryQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFundHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataFundHistoryMapper extends BaseMapper<DataFundHistory> {

    /**
     * 根据基金code查询基金历史数据
     *
     * @param fundCode 基金代码
     * @return 历史数据
     */
    default List<DataFundHistory> selectListByCode(String fundCode) {
        return selectList(new QueryWrapper<DataFundHistory>()
                .eq("code", fundCode));
    }

    /**
     * 批量插入历史数据
     *
     * @param datas 数据集合
     */
    void batchInsert(@Param("datas") List<DataFundHistory> datas);

    /**
     * 查询没有历史数据的基金code
     *
     * @return 基金code列表
     */
    List<String> selectNotHistoryFundCodeList();

    /**
     * 基金历史数据分页查询
     *
     * @param qo 高级查询条件
     * @return 分页结果
     */
    List<DataFundHistory> queryForPage(FundHistoryQueryObject qo);
}
