package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.system.SystemDeptVo;

/**
 * 部门管理业务层
 */
public interface ISystemDeptService {

    /**
     * 新增部门
     *
     * @param vo
     */
    ServiceResult<?> insertSystemDeptInfo(SystemDeptVo vo);
}
