package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;

import java.util.List;

public interface IMenuSettingService {

    ServiceResult<Object> listInfo();

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    ServiceResult deleteInfo(Long id);

    /**
     * 添加菜单
     *
     * @param vo
     * @return
     */
    ServiceResult insertInfo(SystemMenuInfoVo vo);

    /**
     * 菜单详情
     *
     * @param id
     * @return
     */
    ServiceResult detailInfo(Long id);

    /**
     * 更新菜单
     *
     * @param vo
     * @return
     */
    ServiceResult updateInfo(SystemMenuInfoVo vo);

    /**
     * 初始化菜单Code
     *
     * @return
     */
    List<SystemMenu> initMenuCode();

    /**
     * 置顶菜单
     *
     * @param id
     * @return
     */
    ServiceResult topInfo(Long id);

    /**
     * 初始化管理员菜单
     */
    void initSystemUserAdmin();
}
