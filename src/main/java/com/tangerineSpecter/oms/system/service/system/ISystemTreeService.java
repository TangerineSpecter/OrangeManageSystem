package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.dto.system.DeptLevelDto;

import java.util.List;

/**
 * 系统树结构
 */
public interface ISystemTreeService {

    List<DeptLevelDto> deptTree();
}
