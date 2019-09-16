package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemMenuInfoVo;

public interface IMenuSetting {

    ServiceResult<Object> listInfo();

    ServiceResult deleteInfo(Long id);

    ServiceResult insertInfo(SystemMenuInfoVo vo);

    ServiceResult detailInfo(Long id);

    ServiceResult updateInfo(SystemMenuInfoVo vo);
}
