package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.DeptLevelDto;
import com.tangerinespecter.oms.system.domain.entity.SystemDept;
import com.tangerinespecter.oms.system.mapper.SystemDeptMapper;
import com.tangerinespecter.oms.system.service.system.ISystemTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class SystemTreeServiceImpl implements ISystemTreeService {

    @Resource
    private SystemDeptMapper systemDeptMapper;

    @Override
    public List<DeptLevelDto> deptTree() {
        List<SystemDept> deptList = systemDeptMapper.selectList(null);
        List<DeptLevelDto> dtoList = CollUtil.newArrayList();
        for (SystemDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     * 将列表转换成Tree结构
     *
     * @param deptLevelList
     * @return
     */
    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollUtil.isEmpty(deptLevelList)) {
            return CollUtil.newArrayList();
        }
        Multimap<String, DeptLevelDto> levelDtoMultiMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = CollUtil.newArrayList();
        for (DeptLevelDto dto : deptLevelList) {
            levelDtoMultiMap.put(dto.getLevel(), dto);
            if (SystemUtils.LEVEL_ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //按照从大到小排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o2.getSort() - o1.getSort();
            }
        });
        transformDeptTree(deptLevelList, SystemUtils.LEVEL_ROOT, levelDtoMultiMap);
        return rootList;
    }

    /**
     * 递归生成Tree
     */
    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDtoMultiMap) {
        for (int index = 0; index < deptLevelList.size(); index++) {
            //遍历每一层的元素
            DeptLevelDto deptLevelDto = deptLevelList.get(index);
            //处理当前层级的数据
            String nextLevel = SystemUtils.calculateLevel(level, deptLevelDto.getId());
            //处理下一层级
            List<DeptLevelDto> nextDeptList = (List<DeptLevelDto>) levelDtoMultiMap.get(nextLevel);
            if (CollUtil.isNotEmpty(nextDeptList)) {
                //排序
                Collections.sort(nextDeptList, new Comparator<DeptLevelDto>() {
                    @Override
                    public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                        return o2.getSort() - o1.getSort();
                    }
                });
                //设置下一层部门
                deptLevelDto.setDeptLevelList(nextDeptList);
                //进入到下一层处理
                transformDeptTree(nextDeptList, nextLevel, levelDtoMultiMap);
            }
        }
    }
}
