package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.mapper.SystemUserMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.AccountInfo;
import com.tangerinespecter.oms.system.domain.vo.system.SystemUserInfoVo;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.system.ISystemUserService;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    private RedisHelper redisHelper;

    /**
     * 校验登录
     */
    @Override
    public ServiceResult<Object> verifyLogin(HttpServletResponse response, @Valid AccountInfo model) {
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
    public ServiceResult<Object> querySystemUserList(SystemUserQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemUser> pageList = systemUserMapper.queryForPage(qo);
        // 得到分页结果对象
        PageInfo<SystemUser> systemUserInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, systemUserInfo.getTotal());
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

    @Override
    public ServiceResult updatePassword(SystemUserInfoVo vo) {
        SystemUser systemUser = systemUserMapper.selectById(vo.getId());
        if (systemUser == null) {
            return ServiceResult.error(RetCode.ACCOUNTS_NOT_EXIST);
        }
        String oldPassword = SystemUtils.handleUserPassword(vo.getOldPassword(), systemUser.getSalt());
        if (!systemUser.getPassword().equals(oldPassword)) {
            return ServiceResult.error(RetCode.ACCOUNTS_PASSWORD_OLD_ERROR);
        }
        String newPassword = SystemUtils.handleUserPassword(vo.getPassword(), systemUser.getSalt());
        systemUserMapper.updatePassword(vo.getId(), newPassword);
        return ServiceResult.success();
    }

    public static void main(String[] args) {
        money20();
        //moneyAll();
    }

    private static void money20() {
        System.out.println("=====开始储备金方式=====");
        Set<Integer> set = new TreeSet<>();
        for (int b = 0; b < 10000; b++) {
            int a = 50;
            int m = 50000;
            int c = 100000;
            int x = 0;
            int count = 0;
            while (true) {
                int i = RandomUtil.randomInt(100);
                if (i < a) {
                    //减半
                    double div = NumberUtil.div(m, 2);
                    int radio = RandomUtil.randomInt(3, 9);
                    double div1 = NumberUtil.div(radio, 100);
                    double mul = NumberUtil.mul(m, div1);
                    m = m - (int) mul;
                } else {
                    //减半
                    double div = NumberUtil.div(m, 2);
                    int radio = RandomUtil.randomInt(20, 30);
                    double div1 = NumberUtil.div(radio, 100);
                    double mul = NumberUtil.mul(m, div1);
                    m = m + (int) mul;
//                    m += 3000;
                }
                count++;
                if (m >= c) {
                    x += (m - c);
                    m = c;
                } else {
                    int d = c - m;
                    if (x > d) {
                        x -= d;
                        m = c;
                    } else {
                        m += x;
                    }
                }
//                System.out.println("当前本金:" + m);
//                System.out.println("当前储备金:" + x);
                if (x + m <= 1000) {
//                    System.out.println("失败");
                    count = -1;
                    break;
                } else if (x + m >= 1000000) {
//                    System.out.println("成功");
                    break;
                }
//                System.out.println("当前资金" + m);
                //System.out.println(i);
            }
            set.add(count);
            //System.out.println("经历" + count + "周");
        }
        System.out.println(set);
        System.out.println("====结束储备金方式=====");
    }

    private static void moneyAll() {
        Set<Integer> set = new TreeSet<>();
        for (int b = 0; b < 1000000; b++) {
            int a = 50;
            int m = 50000;
            int count = 0;
            while (true) {
                int i = RandomUtil.randomInt(100);
                if (i < a) {
                    double div = NumberUtil.div(m, 2);
                    double mul = NumberUtil.mul(m, 0.05);
                    m = m - (int) mul;
                } else {
                    double div = NumberUtil.div(m, 2);
                    double mul = NumberUtil.mul(m, 0.2);
                    m = m + (int) mul;
//                    m += 3000;
                }
                count++;
                if (m <= 1000) {
//                    System.out.println("失败");
                    count = -1;
                    break;
                } else if (m >= 1000000) {
//                    System.out.println("成功");
                    break;
                }
//                System.out.println("当前资金" + m);
                //System.out.println(i);
            }
            set.add(count);
            //System.out.println("经历" + count + "周");
        }
        System.out.println(set);
    }
}
