package com.tangerinespecter.oms.system.convert.user;

import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HealthConvert {

    HealthConvert INSTANCE = Mappers.getMapper(HealthConvert.class);

    UserHealth convert(UserHealthInfoVo data);
}
