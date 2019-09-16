package com.tangerinespecter.oms.system.service.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.dao.SystemUserMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
public class SystemUserService {

    private final String COOKIE_NAME_TOKEN = "token";

    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 校验登录
     */
    public ServiceResult verifyLogin(HttpServletResponse response, @Valid AccountInfo model) {
        SystemUser systemUser = systemUserMapper.selectOneByUserName(model.getUsername());
        if (systemUser == null) {
            return ServiceResult.error(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
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
        systemUser.setLoginCount(systemUser.getLoginCount() + 1);
        systemUser.setLastLoginDate(DateUtil.now());
        systemUser.setUpdateTime(DateUtil.now());
        systemUserMapper.updateById(systemUser);
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
    public ServiceResult querySystemUserList(SystemUserQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemUser> pageList = systemUserMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemUser> systemUserInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, systemUserInfo.getTotal());
    }

    /**
     * 获取管理员信息
     */
    public void getSystemInfo(Model model, Long id) {
        model.addAttribute("systemUserInfo", systemUserMapper.selectById(id));
    }

    /**
     * 更新账户信息
     */
    public ServiceResult updateSystemUserInfo(SystemUserInfoVo systemUser) {
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
                .setSex(systemUser.getSex());
        systemUserMapper.updateById(info);
        SystemUtils.refreshSession(info);
        return ServiceResult.success();
    }

    public ServiceResult insertSystemUserInfo(SystemUser systemUser) throws Exception {
        if (StrUtil.isBlank(systemUser.getUsername()) || StrUtil.isBlank(systemUser.getPassword())) {
            return ServiceResult.paramError();
        }
        SystemUser user = systemUserMapper.selectOneByUserName(systemUser.getUsername());
        if (user != null) {
            return ServiceResult.error(RetCode.REGISTER_REPEAT);
        }
        String userSlat = SystemUtils.createUserSlat();
        String password = SystemUtils.handleUserPassword(systemUser.getPassword(), userSlat);
        SystemUser userInfo = SystemUser.builder().username(systemUser.getUsername()).password(password)
                .admin(systemUser.getAdmin()).avatar(systemUser.getAvatar())
                .city(systemUser.getCity()).birthday(systemUser.getBirthday())
                .email(systemUser.getEmail()).brief(systemUser.getBrief())
                .nickName(systemUser.getNickName()).sex(systemUser.getSex())
                .phoneNumber(systemUser.getPhoneNumber()).isDel(CommonConstant.IS_DEL_NO)
                .salt(userSlat).build();
        systemUserMapper.insert(userInfo);
        return ServiceResult.success();
    }

}
