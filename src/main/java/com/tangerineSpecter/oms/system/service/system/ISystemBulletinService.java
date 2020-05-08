package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import org.springframework.ui.Model;

public interface ISystemBulletinService {

    ServiceResult queryForPage(Model model, SystemBulletinQueryObject qo);

    ServiceResult insert(SystemBulletinInfoVo data);

    ServiceResult update(SystemBulletinInfoVo data);

    ServiceResult delete(Long id);

    ServiceResult topInfo(Long id);
}
