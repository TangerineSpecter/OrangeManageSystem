package com.tangerinespecter.oms.system.service.user;

import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.user.UserHealthInfoVo;
import org.springframework.ui.Model;

public interface IUserHealthManageService {

    ServiceResult queryForPage(Model model, UserHealthQueryObject qo);

    ServiceResult insert(Integer type);

    ServiceResult update(UserHealthInfoVo data);

    ServiceResult delete(Long id);
}
