package com.tangerinespecter.oms.system.service.system;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constant.CommonConstant;
import com.tangerinespecter.oms.common.constant.RetCode;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountsInfo;
import com.tangerinespecter.oms.system.domain.vo.SystemUserInfoVo;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
    private PageResultService pageResultService;
    @Resource
    private SystemHelper systemHelper;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 校验登录
     */
    public ServiceResult verifyLogin(HttpServletResponse response, @Valid AccountsInfo model) {
        SystemUser systemUser = systemUserMapper.selectOneByUserName(model.getUsername());
        if (systemUser == null) {
            return ServiceResult.error(RetCode.REGISTER_ACCOUNTS_NOT_EXIST);
        }
        try {
            String md5Pwd = systemHelper.handleUserPassword(model.getPassword(), systemUser.getSalt());
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
        systemUserMapper.updateById(systemUser);
        //生成Cookie
        String token = IdUtil.simpleUUID();
        redisHelper.set(token, systemUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //cookie.setMaxAge();
        cookie.setPath("/");
        response.addCookie(cookie);
        return ServiceResult.success();
    }

    /**
     * 后台管理员列表
     */
    public void querySystemUserList(Model model, SystemUserQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemUser> pageList = systemUserMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemUser> systemUserInfo = new PageInfo<>(pageList);
        pageResultService.queryForPage(model, systemUserInfo.getList(), systemUserInfo.getTotal(), qo.getPage(),
                systemUserInfo.getPages());
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
        systemHelper.refreshSession(info);
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
        String userSlat = systemHelper.createUserSlat();
        String password = systemHelper.handleUserPassword(systemUser.getPassword(), userSlat);
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
