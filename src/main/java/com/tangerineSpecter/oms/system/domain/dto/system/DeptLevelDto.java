package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.collection.CollUtil;
import com.tangerinespecter.oms.system.domain.entity.SystemDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptLevelDto extends SystemDept {

    private List<DeptLevelDto> deptLevelList = CollUtil.newArrayList();

    public static DeptLevelDto adapt(SystemDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }
}
