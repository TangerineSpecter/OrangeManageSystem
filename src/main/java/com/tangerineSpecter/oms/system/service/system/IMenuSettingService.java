package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;

import java.util.List;

public interface IMenuSettingService {

    /**
     * 菜单列表
     *
     * @return 菜单列表
     */
    PageInfo<SystemMenu> listInfo();

    /**
     * 删除菜单
     *
     * @param id 菜单id
     */
    void deleteInfo(Long id);

    /**
     * 添加菜单
     *
     * @param vo 菜单信息
     */
    void insertInfo(SystemMenuInfoVo vo);

    /**
     * 菜单详情
     *
     * @param id 菜单id
     * @return 菜单信息
     */
    SystemMenu detailInfo(Long id);

    /**
     * 更新菜单
     *
     * @param vo 菜单信息
     */
    void updateInfo(SystemMenuInfoVo vo);

    /**
     * 初始化菜单Code
     *
     * @return 菜单初始化结果
     */
    List<SystemMenu> initMenuCode();

    /**
     * 置顶菜单
     *
     * @param id 菜单id
     */
    void topInfo(Long id);

    /**
     * 初始化管理员菜单
     */
    void initSystemUserAdmin();
}
