package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.config.CosConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.CosClient;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.SystemUserListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.entity.SystemUserRole;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserPwdVo;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.system.mapper.SystemUserRoleMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TangerineSpecter
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements ISystemUserService {

    private final String COOKIE_NAME_TOKEN = "token";

    private final SystemUserMapper systemUserMapper;
    private final SystemRoleMapper systemRoleMapper;
    private final SystemUserRoleMapper systemUserRoleMapper;
    private final CosClient cosClient;
    private final RedisHelper redisHelper;
    private final SystemHelper systemHelper;

    @Override
    public void verifyLogin(HttpServletRequest request, HttpServletResponse response, @Validated AccountInfo model) {
        Assert.isTrue(CaptchaUtil.ver(model.getCaptcha(), request), () -> new BusinessException(RetCode.VERIFY_CODE_ERROR));
        SystemUser systemUser = systemUserMapper.selectOneByUserName(model.getUsername());
        Assert.isTrue(systemUser != null, () -> new BusinessException(RetCode.REGISTER_ACCOUNTS_NOT_EXIST));
        Assert.isTrue(model.getPassword()
            .length() >= SystemConstant.PASSWORD_DEFAULT_MIN_LENGTH, () -> new BusinessException(RetCode.PASSWORD_LENGTH_TOO_SHORT));
        try {
            String md5Pwd = SystemUtils.handleUserPassword(model.getPassword(), systemUser.getSalt());
            UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), md5Pwd);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            SystemHelper.ACTIVE_USERS_MANAGE.put(model.getUsername(), subject.getSession());
        } catch (UnknownAccountException e) {
            log.error("[帐号登录异常]:", e);
            throw new BusinessException(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            log.error("[帐号登录异常]:", e);
            throw new BusinessException(RetCode.ACCOUNTS_PASSWORD_ERROR);
        }
        systemUserMapper.updateLoginCountById(systemUser.getId(), DateUtil.now(), DateUtil.now());
        log.info("用户：{}在时间{}进行了登录,登录地址{}", systemUser.getUsername(), DateUtil.now(), ServletUtil.getClientIP(request));
        //生成Cookie
//        String token = IdUtil.simpleUUID();
//        redisHelper.set(token, systemUser);
//        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
//        cookie.setMaxAge(1);
//        cookie.setPath("/");
//        response.addCookie(cookie);
    }

    @Override
    public List<SystemUserListDto> list(SystemUserQueryObject qo) {
        return this.querySystemUserList(systemUserMapper.queryForPage(qo));
    }

    public List<SystemUserListDto> querySystemUserList(List<SystemUserListDto> pageList) {
        final List<String> activeUsers = systemHelper.getActiveUsers();
        List<SystemRole> allRoles = systemRoleMapper.selectAllList();
        CollUtils.forEach(pageList, user -> {
            List<SystemRole> haveRoles = systemRoleMapper.selectRoleByUid(user.getUid());
            user.setRoles(allRoles);
            user.setHaveRoles(haveRoles);
            user.setHaveRoleIds(CollUtils.convertList(haveRoles, SystemRole::getId));
            user.setActive(activeUsers.contains(user.getUsername()));
        });
        // 得到分页结果对象
        return pageList;
    }

    @Override
    public void getSystemInfo(Model model, Long id) {
        model.addAttribute("systemUserInfo", systemUserMapper.selectById(id));
    }

    @Override
    public void updateSystemUserInfo(SystemUserInfoVo systemUser) {
        SystemUser currentUser = UserContext.getCurrentUser();
        currentUser.setNickName(systemUser.getNickName());
        currentUser.setSex(systemUser.getSex());
        currentUser.setCity(systemUser.getCity());
        currentUser.setBrief(systemUser.getBrief());
        currentUser.setEmail(systemUser.getEmail());
        currentUser.setPhoneNumber(systemUser.getPhoneNumber());
        systemUserMapper.updateUserInfo(currentUser);
        this.refreshSession(currentUser);
    }

    @Override
    public SystemUser insertSystemUserInfo(SystemUser systemUser) {
        //TODO改用唯一索引
        SystemUser user = systemUserMapper.selectOneByUserName(systemUser.getUsername());
        Assert.isTrue(user == null, () -> new BusinessException(RetCode.REGISTER_REPEAT));
        String userSalt = SystemUtils.createUserSalt();
        String password = SystemUtils.handleUserPassword(systemUser.getPassword(), userSalt);
        SystemUser userInfo = SystemUser.builder().uid(SystemUtils.createUid(userSalt))
            .username(systemUser.getUsername()).password(password).admin(systemUser.getAdmin())
            .avatar(systemUser.getAvatar()).city(systemUser.getCity()).birthday(systemUser.getBirthday())
            .email(systemUser.getEmail()).brief(systemUser.getBrief()).nickName(systemUser.getNickName())
            .sex(systemUser.getSex()).phoneNumber(systemUser.getPhoneNumber())
            .isDel(GlobalBoolEnum.FALSE.getValue()).salt(userSalt).build();
        systemUserMapper.insert(userInfo);
        return userInfo;
    }

    @Override
    public void updatePassword(SystemUserPwdVo vo) {
        SystemUser systemUser = UserContext.getCurrentUser();
        Assert.isTrue(systemUser != null, () -> new BusinessException(RetCode.ACCOUNTS_NOT_EXIST));
        String oldPassword = SystemUtils.handleUserPassword(vo.getOldPassword(), systemUser.getSalt());
        Assert.isTrue(systemUser.getPassword()
            .equals(oldPassword), () -> new BusinessException(RetCode.ACCOUNTS_PASSWORD_OLD_ERROR));
        String newPassword = SystemUtils.handleUserPassword(vo.getPassword(), systemUser.getSalt());
        systemUserMapper.updatePassword(systemUser.getId(), newPassword);
    }

    @Override
    public void updateSystemUserRole(SystemUserInfoVo vo) {
        if (CharSequenceUtil.isBlank(vo.getRoleIds())) {
            systemUserRoleMapper.deleteByUid(vo.getId());
            return;
        }
        Set<Long> haveRoleIds = systemUserRoleMapper.getHaveRoleIdsByUid(vo.getId());
        //请求roleIds
        List<Long> rolesReq = Splitter.on(",").omitEmptyStrings().splitToList(vo.getRoleIds()).parallelStream()
            .map(Long::parseLong).collect(Collectors.toList());
        Collection<Long> intersectionIds = CollUtil.intersection(rolesReq, haveRoleIds);
        //移除共同部分
        rolesReq.removeAll(intersectionIds);
        haveRoleIds.removeAll(intersectionIds);
        //新增角色
        rolesReq.forEach(r -> systemUserRoleMapper.insert(SystemUserRole.builder().rid(r).uid(vo.getUid()).build()));
        //移除角色
        haveRoleIds.forEach(r -> systemUserRoleMapper.delete(new UpdateWrapper<SystemUserRole>().eq("rid", r)
            .eq("uid", vo.getId())));
    }

    @Override
    public void updateAvatar(FileInfoBean avatarInfo) {
        SystemUser currentUser = UserContext.getCurrentUser();
        String beforeAvatar = currentUser.getAvatar();
        currentUser.setAvatar(avatarInfo.getName());
        systemUserMapper.updateById(currentUser);
        //清理cos头像资源
        cosClient.delete(CosConfig.AVATAR_ZONE, beforeAvatar);
        this.refreshSession(currentUser);
    }

    /**
     * 刷新当前用户session
     *
     * @param info 管理员信息
     */
    public void refreshSession(SystemUser info) {
        SystemUser systemUser = UserContext.getCurrentUser();
        systemUser.setUsername(info.getUsername());
        systemUser.setPhoneNumber(info.getPhoneNumber());
        systemUser.setCity(info.getCity());
        systemUser.setSex(info.getSex());
        systemUser.setEmail(info.getEmail());
        systemUser.setBrief(info.getBrief());
        systemUser.setBirthday(info.getBirthday());
        systemUser.setNickName(info.getNickName());
        cosClient.initAvatar(info);
    }

    @Override
    public void offline(String username) {
        systemHelper.offline(username);
    }

    @Override
    public List<String> getUseridList(String uid) {
        if (StrUtil.isNotBlank(uid)) {
            return CollUtil.newArrayList(uid);
        }
        return CollUtils.convertList(this.list(new SystemUserQueryObject()), SystemUserListDto::getUid);
    }
}
