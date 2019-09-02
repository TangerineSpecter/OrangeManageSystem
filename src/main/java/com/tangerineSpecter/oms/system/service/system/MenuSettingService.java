package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.system.MenuListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
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
}
