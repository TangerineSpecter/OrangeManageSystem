package com.tangerinespecter.oms.system.convert.system;

import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.BaseConvert;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface PermissionConvert extends BaseConvert {

    PermissionConvert INSTANCE = Mappers.getMapper(PermissionConvert.class);

    @Mappings({
            @Mapping(target = "name", expression = "java(menu.getTitle() + \"权限\")"),
            @Mapping(target = "code", expression = "java(getPermissionCode(menu.getPermissionCode()))"),
            @Mapping(target = "url", expression = "java(getPermissionUrl(menu.getHref()))")
    })
    SystemPermission convert(SystemMenu menu);

    /**
     * 获取权限code
     *
     * @param permissionCode 菜单权限code
     * @return 权限code
     */
    @Named("getPermissionCode")
    default String getPermissionCode(String permissionCode) {
        return SystemUtils.getPermissionCode(permissionCode);
    }

    /**
     * 获取权限地址
     *
     * @param href 菜单跳转url
     * @return 权限url
     */
    @Named("getPermissionUrl")
    default String getPermissionUrl(String href) {
        return SystemUtils.getPermissionUrl(href);
    }
}
