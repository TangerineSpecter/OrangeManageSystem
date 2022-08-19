package com.tangerinespecter.oms.system.convert.user;

import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface UserConvert extends BaseConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserContext convert(SystemUser systemUser);
}
