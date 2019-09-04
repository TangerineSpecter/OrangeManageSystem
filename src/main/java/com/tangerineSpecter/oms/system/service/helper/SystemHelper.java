package com.tangerinespecter.oms.system.service.helper;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.tangerinespecter.oms.common.constant.CommonConstant;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统帮助类
 *
 * @author TangerineSpecter
 * @date 2019年08月23日01:21:56
 */
@Service
public class SystemHelper {

    /**
     * 处理用户密码
     *
     * @param password
     * @return
     */
    public String handleUserPassword(String password, String salt) {
        password = password.charAt(5) + password.charAt(2) + password + password.charAt(0) + salt;
        return handleUserPassword(password);
    }

    /**
     * 处理用户密码
     *
     * @param password
     * @return
     */
    private String handleUserPassword(String password) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        password = password.charAt(5) + password.charAt(2) + password + password.charAt(0);
        return md5.digestHex(password + CommonConstant.SALT);
    }

    /**
     * 随机生成用户salt
     */
    public String createUserSlat() {
        int randomInt = RandomUtil.randomInt(10);
        String sub = IdUtil.randomUUID().substring(4, 11);
        return handleUserPassword(sub).substring(randomInt, randomInt + 6);
    }

    /**
     * 刷新当前用户session
     *
     * @param info
     */
    public void refreshSession(SystemUser info) {
        SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
        systemUser.setUsername(info.getUsername());
        systemUser.setPhoneNumber(info.getPhoneNumber());
        systemUser.setCity(info.getCity());
        systemUser.setSex(info.getSex());
        systemUser.setEmail(info.getEmail());
        systemUser.setAvatar(info.getAvatar());
        systemUser.setBrief(info.getBrief());
        systemUser.setBirthday(info.getBirthday());
        systemUser.setNickName(info.getNickName());
    }

}
