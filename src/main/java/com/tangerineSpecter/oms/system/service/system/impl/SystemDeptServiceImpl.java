package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemDept;
import com.tangerinespecter.oms.system.domain.vo.system.SystemDeptVo;
import com.tangerinespecter.oms.system.mapper.SystemDeptMapper;
import com.tangerinespecter.oms.system.service.system.ISystemDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SystemDeptServiceImpl implements ISystemDeptService {

    @Resource
    private SystemDeptMapper systemDeptMapper;

    @Override
    public ServiceResult<?> insertSystemDeptInfo(SystemDeptVo vo) {
        if (checkExist(vo.getParentId(), vo.getName(), vo.getId())) {
            return ServiceResult.error(RetCode.DEPT_NAME_EXIST_ON_LEVEL);
        }
        SystemDept dept = SystemDept.builder().name(vo.getName()).parentId(vo.getParentId())
                .sort(vo.getSort()).remark(vo.getRemark()).build();
        dept.setLevel(SystemUtils.calculateLevel(getLevel(vo.getParentId()), dept.getParentId()));
        systemDeptMapper.updateById(dept);
        return ServiceResult.success();
    }

    /**
     * 校验部门是否存在同名
     *
     * @param parentId 父层级ID
     * @param name     部门名称
     * @param deptId   部门ID
     * @return
     */
    private boolean checkExist(Long parentId, String name, Long deptId) {
        return true;
    }

    /**
     * 根据部门ID获取部门Level
     *
     * @param deptId 部门Id
     * @return 部门Level
     */
    private String getLevel(Long deptId) {
        if (deptId == null) {
            return null;
        }
        SystemDept dept = systemDeptMapper.selectById(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
