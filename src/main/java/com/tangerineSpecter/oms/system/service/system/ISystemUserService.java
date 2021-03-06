package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author TangerineSpecter
 */
public interface ISystemUserService {

    /**
     * 校验登录
     */
    ServiceResult<Object> verifyLogin(HttpServletResponse response, @Valid AccountInfo model);

    /**
     * 后台管理员列表
     */
    ServiceResult<Object> querySystemUserList(SystemUserQueryObject qo);

    /**
     * 获取管理员信息
     */
    void getSystemInfo(Model model, Long id);

    /**
     * 更新账户信息
     */
    ServiceResult<Object> updateSystemUserInfo(SystemUserInfoVo systemUser);

    /**
     * 创建管理员
     *
     * @param systemUser 管理员信息
     * @return 创建结果
     */
    ServiceResult<Object> insertSystemUserInfo(SystemUser systemUser);

    /**
     * 修改密码
     */
    ServiceResult updatePassword(SystemUserInfoVo vo);

    /**
     * 更新角色
     * @param vo
     * @return
     */
    ServiceResult updateSystemUserRole(SystemUserInfoVo vo);
}
