package com.tangerinespecter.oms.system.convert.user;

import com.tangerinespecter.oms.system.domain.entity.SystemUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    /**
     * 创建用户角色关联对象
     * @param uid 用户id
     * @param rid 角色id
     * @return 对象
     */
    SystemUserRole create(String uid, Long rid);
}
