package com.tangerinespecter.oms.system.service.data;

import com.tangerinespecter.oms.common.query.FoodLibraryQueryObject;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IDataFoodLibraryService extends BaseService<FoodLibraryQueryObject, DataFoodLibrary> {

    /**
     * 添加食物数据
     *
     * @param vo 添加参数
     */
    void insertInfo(FoodLibraryInfoVo vo);

    /**
     * 编辑食物数据
     *
     * @param vo 编辑参数
     */
    void updateInfo(FoodLibraryInfoVo vo);

    /**
     * 删除食物数据
     *
     * @param id 数据id
     */
    void deleteInfo(Long id);

    /**
     * 食物数据详情
     *
     * @param id 数据id
     * @return 详情数据
     */
    DataFoodLibrary detailInfo(Long id);

}
