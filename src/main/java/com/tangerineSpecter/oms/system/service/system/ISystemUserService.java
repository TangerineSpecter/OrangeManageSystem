package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemUserListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserPwdVo;
import com.tangerinespecter.oms.system.service.BaseService;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author TangerineSpecter
 */
public interface ISystemUserService extends BaseService<SystemUserQueryObject, SystemUserListDto> {

    /**
     * 校验登录
     */
    void verifyLogin(HttpServletRequest request, HttpServletResponse response, @Valid AccountInfo model);

    /**
     * 获取管理员信息
     */
    void getSystemInfo(Model model, Long id);

    /**
     * 修改系统用户信息
     */
    void updateSystemUserInfo(SystemUserInfoVo systemUser);

    /**
     * 创建管理员
     *
     * @param systemUser 管理员信息
     * @return 创建结果
     */
    SystemUser insertSystemUserInfo(SystemUser systemUser);

    /**
     * 修改密码
     */
    void updatePassword(SystemUserPwdVo vo);

    /**
     * 更新角色
     *
     * @param vo
     * @return
     */
    void updateSystemUserRole(SystemUserInfoVo vo);

    /**
     * 更新头像
     *
     * @param avatarInfo 头像文件信息
     */
    void updateAvatar(FileInfoBean avatarInfo);

    /**
     * 下线账号
     *
     * @param username 账号名
     */
    void offline(String username);

    /**
     * 获取账号uid列表
     *
     * @param uid 不传返回全部，uid存在则返回，不存在则空
     * @return 账号uid列表
     */
    List<String> getUseridList(String uid);
}
