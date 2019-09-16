package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public interface ISystemUserService {

    /**
     * 校验登录
     */
    ServiceResult verifyLogin(HttpServletResponse response, @Valid AccountInfo model);

    /**
     * 后台管理员列表
     */
    ServiceResult querySystemUserList(SystemUserQueryObject qo);

    /**
     * 获取管理员信息
     */
    void getSystemInfo(Model model, Long id);

    /**
     * 更新账户信息
     */
    ServiceResult updateSystemUserInfo(SystemUserInfoVo systemUser);

    /**
     * 创建管理员
     *
     * @param systemUser
     * @return
     */
    ServiceResult insertSystemUserInfo(SystemUser systemUser);
}
