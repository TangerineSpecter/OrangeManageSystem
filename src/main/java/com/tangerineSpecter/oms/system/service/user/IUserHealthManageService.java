package com.tangerinespecter.oms.system.service.user;

import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.service.BaseService;

public interface IUserHealthManageService extends BaseService<UserHealthQueryObject, UserHealth> {

    void insert(Integer type);

    void update(UserHealthInfoVo data);

    void delete(Long id);
}
