package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import org.springframework.ui.Model;

public interface ISystemBulletinService {

    /**
     * 公告列表
     */
    PageInfo<SystemBulletin> queryForPage(Model model, SystemBulletinQueryObject qo);

    /**
     * 新增公告
     */
    void insert(SystemBulletinInfoVo data);

    /**
     * 编辑公告
     */
    void update(SystemBulletinInfoVo data);

    /**
     * 删除公告
     */
    void delete(Long id);

    /**
     * 置顶公告
     */
    void topInfo(Long id);
}
