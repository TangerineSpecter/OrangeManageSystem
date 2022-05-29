package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
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
public class SystemUserServiceImpl implements ISystemUserService {

    private final String COOKIE_NAME_TOKEN = "token";

    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 校验登录
     */
    @Override
    public ServiceResult<Object> verifyLogin(HttpServletRequest request, HttpServletResponse response, @Validated AccountInfo model) {
        if (!CaptchaUtil.ver(model.getCaptcha(), request)) {
            return ServiceResult.error(RetCode.VERIFY_CODE_ERROR);
        }
        SystemUser systemUser = systemUserMapper.selectOneByUserName(model.getUsername());
        if (systemUser == null) {
            return ServiceResult.error(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
        }
        if (model.getPassword().length() < SystemConstant.PASSWORD_DEFAULT_MIN_LENGTH) {
            return ServiceResult.error(RetCode.PASSWORD_LENGTH_TOO_SHORT);
        }
        try {
            String md5Pwd = SystemUtils.handleUserPassword(model.getPassword(), systemUser.getSalt());
            UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), md5Pwd);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.error("[帐号登录异常]:", e);
            return ServiceResult.error(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            log.error("[帐号登录异常]:", e);
            return ServiceResult.error(RetCode.ACCOUNTS_PASSWORD_ERROR);
        }
        systemUserMapper.updateLoginCountById(systemUser.getId(), DateUtil.now(), DateUtil.now());
        //生成Cookie
//        String token = IdUtil.simpleUUID();
//        redisHelper.set(token, systemUser);
//        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
//        cookie.setMaxAge(1);
//        cookie.setPath("/");
//        response.addCookie(cookie);
        return ServiceResult.success();
    }

    /**
     * 后台管理员列表
     */
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

    /**
     * 获取管理员信息
     */
    @Override
    public void getSystemInfo(Model model, Long id) {
        model.addAttribute("systemUserInfo", systemUserMapper.selectById(id));
    }

    /**
     * 更新账户信息
     */
    @Override
    public ServiceResult<Object> updateSystemUserInfo(SystemUserInfoVo systemUser) {
        if (systemUser.getId() == null) {
            return ServiceResult.success();
        }
        SystemUser info = systemUserMapper.selectById(systemUser.getId());
        if (info == null) {
            return ServiceResult.error(RetCode.ACCOUNTS_NOT_EXIST);
        }
        info.setNickName(systemUser.getNickName()).setSex(systemUser.getSex())
                .setCity(systemUser.getCity()).setBrief(systemUser.getBrief())
                .setEmail(systemUser.getEmail()).setPhoneNumber(systemUser.getPhoneNumber())
                .setSex(systemUser.getSex()).setAvatar(systemUser.getAvatarUrl());
        systemUserMapper.updateUserInfo(info);
        SystemUtils.refreshSession(info);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult<Object> insertSystemUserInfo(SystemUser systemUser) {
        if (CharSequenceUtil.isBlank(systemUser.getUsername()) || CharSequenceUtil.isBlank(systemUser.getPassword())) {
            return ServiceResult.paramError();
        }
        SystemUser user = systemUserMapper.selectOneByUserName(systemUser.getUsername());
        if (user != null) {
            return ServiceResult.error(RetCode.REGISTER_REPEAT);
        }
        String userSalt = SystemUtils.createUserSalt();
        String password = SystemUtils.handleUserPassword(systemUser.getPassword(), userSalt);
        SystemUser userInfo = SystemUser.builder().uid(SystemUtils.createUid(userSalt))
                .username(systemUser.getUsername()).password(password)
                .admin(systemUser.getAdmin()).avatar(systemUser.getAvatar())
                .city(systemUser.getCity()).birthday(systemUser.getBirthday())
                .email(systemUser.getEmail()).brief(systemUser.getBrief())
                .nickName(systemUser.getNickName()).sex(systemUser.getSex())
                .phoneNumber(systemUser.getPhoneNumber()).isDel(CommonConstant.IS_DEL_NO)
                .salt(userSalt).build();
        systemUserMapper.insert(userInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updatePassword(SystemUserPwdVo vo) {
        SystemUser systemUser = UserContext.getCurrentUser();
        Assert.isTrue(systemUser != null, () -> new BusinessException(RetCode.ACCOUNTS_NOT_EXIST));
        String oldPassword = SystemUtils.handleUserPassword(vo.getOldPassword(), systemUser.getSalt());

        Assert.isTrue(systemUser.getPassword().equals(oldPassword), () -> new BusinessException(RetCode.ACCOUNTS_PASSWORD_OLD_ERROR));
        String newPassword = SystemUtils.handleUserPassword(vo.getPassword(), systemUser.getSalt());
        systemUserMapper.updatePassword(systemUser.getId(), newPassword);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateSystemUserRole(SystemUserInfoVo vo) {
        if (CharSequenceUtil.isBlank(vo.getRoleIds())) {
            systemUserRoleMapper.deleteByUid(vo.getId());
            return ServiceResult.success();
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
        return ServiceResult.success();
    }

}
