package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import com.tangerinespecter.oms.system.service.BaseService;

public interface ISystemBulletinService extends BaseService<SystemBulletinQueryObject, SystemBulletin> {

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
