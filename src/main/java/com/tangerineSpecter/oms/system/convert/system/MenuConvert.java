package com.tangerinespecter.oms.system.convert.system;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface MenuConvert extends BaseConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    SystemMenu convert(SystemMenuInfoVo menu);

}
