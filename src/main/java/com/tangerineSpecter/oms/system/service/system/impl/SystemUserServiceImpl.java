package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.SystemUserListDto;
import com.tangerinespecter.oms.system.domain.entity.SystemRole;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.entity.SystemUserRole;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserPwdVo;
import com.tangerinespecter.oms.system.mapper.SystemRoleMapper;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.system.mapper.SystemUserRoleMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
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
    private final RedisHelper redisHelper;

    @Override
    public void verifyLogin(HttpServletRequest request, HttpServletResponse response, @Validated AccountInfo model) {
        Assert.isTrue(CaptchaUtil.ver(model.getCaptcha(), request), () -> new BusinessException(RetCode.VERIFY_CODE_ERROR));
        SystemUser systemUser = systemUserMapper.selectOneByUserName(model.getUsername());
        Assert.isTrue(systemUser != null, () -> new BusinessException(RetCode.REGISTER_ACCOUNTS_NOT_EXIST));
        Assert.isTrue(model.getPassword().length() >= SystemConstant.PASSWORD_DEFAULT_MIN_LENGTH, () -> new BusinessException(RetCode.PASSWORD_LENGTH_TOO_SHORT));
        try {
            String md5Pwd = SystemUtils.handleUserPassword(model.getPassword(), systemUser.getSalt());
            UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), md5Pwd);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.error("[帐号登录异常]:", e);
            throw new BusinessException(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            log.error("[帐号登录异常]:", e);
            throw new BusinessException(RetCode.ACCOUNTS_PASSWORD_ERROR);
        }
        systemUserMapper.updateLoginCountById(systemUser.getId(), DateUtil.now(), DateUtil.now());
        //生成Cookie
//        String token = IdUtil.simpleUUID();
//        redisHelper.set(token, systemUser);
//        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
//        cookie.setMaxAge(1);
//        cookie.setPath("/");
//        response.addCookie(cookie);
    }

    @Override
    public PageInfo<SystemUserListDto> querySystemUserList(SystemUserQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemUserListDto> pageList = systemUserMapper.queryForPage(qo);
        List<SystemRole> allRoles = systemRoleMapper.selectAllList();
        pageList.forEach(u -> {
            List<SystemRole> haveRoles = systemRoleMapper.selectRoleByUid(u.getUid());
            u.setRoles(allRoles);
            u.setHaveRoles(haveRoles);
            List<Long> haveRoleIds = haveRoles.stream().map(SystemRole::getId).collect(Collectors.toList());
            u.setHaveRoleIds(haveRoleIds);
        });
        // 得到分页结果对象
        return new PageInfo<>(pageList);
    }

    @Override
    public void getSystemInfo(Model model, Long id) {
        model.addAttribute("systemUserInfo", systemUserMapper.selectById(id));
    }

    @Override
    public void updateSystemUserInfo(SystemUserInfoVo systemUser) {
        SystemUser info = systemUserMapper.selectById(systemUser.getId());
        Assert.isTrue(info != null, () -> new BusinessException(RetCode.ACCOUNTS_NOT_EXIST));
        info.setNickName(systemUser.getNickName()).setSex(systemUser.getSex())
                .setCity(systemUser.getCity()).setBrief(systemUser.getBrief())
                .setEmail(systemUser.getEmail()).setPhoneNumber(systemUser.getPhoneNumber())
                .setSex(systemUser.getSex()).setAvatar(systemUser.getAvatarUrl());
        systemUserMapper.updateUserInfo(info);
        SystemUtils.refreshSession(info);
    }

    @Override
    public SystemUser insertSystemUserInfo(SystemUser systemUser) {
        //TODO改用唯一索引
        SystemUser user = systemUserMapper.selectOneByUserName(systemUser.getUsername());
        Assert.isTrue(user == null, () -> new BusinessException(RetCode.REGISTER_REPEAT));
        String userSalt = SystemUtils.createUserSalt();
        String password = SystemUtils.handleUserPassword(systemUser.getPassword(), userSalt);
        SystemUser userInfo = SystemUser.builder().uid(SystemUtils.createUid(userSalt))
                .username(systemUser.getUsername()).password(password)
                .admin(systemUser.getAdmin()).avatar(systemUser.getAvatar())
                .city(systemUser.getCity()).birthday(systemUser.getBirthday())
                .email(systemUser.getEmail()).brief(systemUser.getBrief())
                .nickName(systemUser.getNickName()).sex(systemUser.getSex())
                .phoneNumber(systemUser.getPhoneNumber()).isDel(GlobalBoolEnum.YES.getValue())
                .salt(userSalt).build();
        systemUserMapper.insert(userInfo);
        return userInfo;
    }

    @Override
    public void updatePassword(SystemUserPwdVo vo) {
        SystemUser systemUser = UserContext.getCurrentUser();
        Assert.isTrue(systemUser != null, () -> new BusinessException(RetCode.ACCOUNTS_NOT_EXIST));
        String oldPassword = SystemUtils.handleUserPassword(vo.getOldPassword(), systemUser.getSalt());
        Assert.isTrue(systemUser.getPassword().equals(oldPassword), () -> new BusinessException(RetCode.ACCOUNTS_PASSWORD_OLD_ERROR));
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
        List<Long> rolesReq = Splitter.on(",").omitEmptyStrings().splitToList(vo.getRoleIds())
                .parallelStream().map(Long::parseLong).collect(Collectors.toList());
        Collection<Long> intersectionIds = CollUtil.intersection(rolesReq, haveRoleIds);
        //移除共同部分
        rolesReq.removeAll(intersectionIds);
        haveRoleIds.removeAll(intersectionIds);
        //新增角色
        rolesReq.forEach(r -> systemUserRoleMapper.insert(SystemUserRole.builder().rid(r).uid(vo.getUid()).build()));
        //移除角色
        haveRoleIds.forEach(r -> systemUserRoleMapper.delete(new UpdateWrapper<SystemUserRole>().eq("rid", r).eq("uid", vo.getId())));
    }

}
