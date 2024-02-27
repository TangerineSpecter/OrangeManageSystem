package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 系统工具类
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 14:37:26
 */
@Slf4j
public class SystemUtils {

    /**
     * Linux CPU信息文件路径
     */
    private static final String LINUX_CPU_INFO_FILE = "/proc/stat";
    /**
     * Linux 内存信息文件路径
     */
    private static final String LINUX_MEMORY_INFO_FILE = "/proc/meminfo";
    /**
     * 默认为0
     */
    private static final String DEFAULT_ZERO = "0";
    /**
     * 无数据
     */
    private static final String DEFAULT_NONE = "none";
    /**
     * 部门Level分隔符
     */
    public static final String LEVEL_SEPARATOR = ".";
    /**
     * 部门Level根路径
     */
    public static final String LEVEL_ROOT = "0";


    /**
     * 获取操作系统
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 处理用户密码
     *
     * @param password
     * @return
     */
    public static String handleUserPassword(String password, String salt) {
        password = password.charAt(5) + password.charAt(2) + password + password.charAt(0) + salt;
        return handleUserPassword(password);
    }

    /**
     * 处理用户密码
     *
     * @param password
     * @return
     */
    private static String handleUserPassword(String password) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        password = password.charAt(5) + password.charAt(2) + password + password.charAt(0);
        return md5.digestHex(password + CommonConstant.SALT);
    }

    /**
     * 随机生成用户salt
     */
    public static String createUserSalt() {
        int randomInt = RandomUtil.randomInt(10);
        String sub = IdUtil.randomUUID().substring(4, 11);
        return handleUserPassword(sub).substring(randomInt, randomInt + 6);
    }

    /**
     * 生成uid
     *
     * @param salt 用户盐
     * @return 根据用户盐生成账户id
     */
    public static String createUid(String salt) {
        return CharSequenceUtil.sub(DigestUtil.sha256Hex(salt + DateUtil.now()), 0, 12) + DateUtil.format(new Date(), "MMdd");
    }

    /**
     * 生成当前部门的Level
     *
     * @param parenLevel 父级部门的level
     * @param parentId   父级部门ID
     * @return 生成的部门Level, 如0.1.2
     */
    public static String calculateLevel(String parenLevel, Long parentId) {
        if (CharSequenceUtil.isBlank(parenLevel)) {
            return LEVEL_ROOT;
        } else {
            return StrUtil.join(parenLevel, LEVEL_SEPARATOR, parentId);
        }
    }

    /**
     * 获取菜单Code
     *
     * @param href   菜单跳转地址
     * @param menuId 菜单ID
     * @return 菜单code
     */
    public static String getMenuCode(String href, Long menuId) {
        return SecureUtil.md5(CommonConstant.MENU_CODE + href + menuId);
    }

    /**
     * 获取权限code
     *
     * @param permissionCode 菜单权限code
     * @return 权限code
     */
    public static String getPermissionCode(String permissionCode) {
        return SecureUtil.md5(permissionCode + CommonConstant.PERMISSION_CODE);
    }

    /**
     * 获取权限地址
     *
     * @param href 菜单跳转url
     * @return 权限url
     */
    public static String getPermissionUrl(String href) {
        if (CharSequenceUtil.isBlank(href)) {
            return null;
        }
        List<String> resultUrl = Splitter.on("/").omitEmptyStrings().splitToList(href);
        return Joiner.on(":").join(resultUrl);
    }

}
