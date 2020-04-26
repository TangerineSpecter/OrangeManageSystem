package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;

import java.util.List;

public interface IMenuSettingService {

    ServiceResult<Object> listInfo();

    ServiceResult deleteInfo(Long id);

    ServiceResult insertInfo(SystemMenuInfoVo vo);

    ServiceResult detailInfo(Long id);

    ServiceResult updateInfo(SystemMenuInfoVo vo);

    List<SystemMenu> initMenuCode();

    ServiceResult topInfo(Long id);
}
