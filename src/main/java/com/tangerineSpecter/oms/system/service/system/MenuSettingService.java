package com.tangerinespecter.oms.system.service.system;

import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.vo.SystemMenuInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuSettingService {

    @Resource
    private SystemMenuMapper systemMenuMapper;

    public ServiceResult<Object> listInfo() {
        List<SystemMenu> list = systemMenuMapper.selectList(null);
        return ServiceResult.pageSuccess(list, (long) list.size());
    }

    public ServiceResult deleteInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        systemMenuMapper.deleteById(id);
        return ServiceResult.success();
    }

    public ServiceResult insertInfo(SystemMenuInfoVo vo) {
        SystemMenu systemMenu = SystemMenu.builder().title(vo.getTitle()).href(vo.getHref())
                .icon("fa " + vo.getIcon()).level(vo.getLevel()).pid(vo.getPid())
                .target(vo.getTarget()).sort(vo.getSort()).build();
        systemMenuMapper.insert(systemMenu);
        return ServiceResult.success();
    }
}
