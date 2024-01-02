package com.tangerinespecter.oms.system.convert.system;

import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import com.tangerinespecter.oms.system.domain.entity.SystemToken;
import com.tangerinespecter.oms.system.domain.vo.system.SystemConfigInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemTokenVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统信息相关转换
 *
 * @author 丢失的橘子
 */
@Mapper
public interface SystemConvert extends BaseConvert {

    SystemConvert INSTANCE = Mappers.getMapper(SystemConvert.class);

    SystemConfig convert(SystemConfigInfoVo param);

    SystemToken convert(SystemTokenVo param);
}
