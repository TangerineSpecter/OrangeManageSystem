package com.tangerinespecter.oms.system.convert.data;

import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Mapper
@SuppressWarnings("all")
public interface FoodConvert extends BaseConvert {

    FoodConvert INSTANCE = Mappers.getMapper(FoodConvert.class);

    @Mappings(
            @Mapping(target = "calories", expression = "java(calCalories(vo))")
    )
    DataFoodLibrary convert(FoodLibraryInfoVo vo);

    @Named("calCalories")
    default BigDecimal calCalories(FoodLibraryInfoVo vo) {
        if (!BigDecimal.ZERO.equals(vo.getCalories())) {
            return vo.getCalories();
        }
        //能量比，脂肪9：蛋白质4：碳水4
        return NumChainCal.startOf(NumChainCal.startOf(vo.getFat()).mul(9).getBigDecimal())
                .add(NumChainCal.startOf(vo.getCarbs()).mul(4).getBigDecimal())
                .add(NumChainCal.startOf(vo.getProtein()).mul(4).getBigDecimal())
                .getBigDecimal();
    }
}
