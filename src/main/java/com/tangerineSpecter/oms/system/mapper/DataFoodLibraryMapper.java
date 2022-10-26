package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.FoodLibraryQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;

import java.util.List;

public interface DataFoodLibraryMapper extends BaseMapper<DataFoodLibrary> {

    /**
     * 分页查询
     *
     * @param qo 高级查询参数
     * @return 分页结果
     */
    List<DataFoodLibrary> queryForPage(FoodLibraryQueryObject qo);
}
