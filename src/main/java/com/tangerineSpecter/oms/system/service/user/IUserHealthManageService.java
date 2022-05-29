package com.tangerinespecter.oms.system.service.user;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import org.springframework.ui.Model;

public interface IUserHealthManageService {

    PageInfo<UserHealth> queryForPage(Model model, UserHealthQueryObject qo);

    void insert(Integer type);

    void update(UserHealthInfoVo data);

    void delete(Long id);
}
