package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
@SuppressWarnings("all")
public interface FoodConvert extends BaseConvert {

    FoodConvert INSTANCE = Mappers.getMapper(FoodConvert.class);

    DataFoodLibrary convert(FoodLibraryInfoVo vo);
}
