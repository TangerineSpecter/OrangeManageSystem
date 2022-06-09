package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemPermissionQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemPermission;

public interface IPermissionManageService {

    /**
     * 权限列表
     *
     * @param qo 查询分页参数
     * @return 分页结果
     */
    PageInfo<SystemPermission> queryForPage(SystemPermissionQueryObject qo);

    /**
     * 权限初始化
     */
    void init();

    /**
     * 删除权限
     *
     * @param code 权限code
     */
    void deleteInfo(String code);
}
