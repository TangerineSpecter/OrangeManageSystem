package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.config.QiNiuConfig;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
    public final static String LEVEL_SEPARATOR = ".";
    /**
     * 部门Level根路径
     */
    public final static String LEVEL_ROOT = "0";


    /**
     * 获取操作系统
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static SystemUser getCurrentUser() {
        SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
        if (systemUser == null) {
            return null;
        }
        String avatar = systemUser.getAvatar();
        if (!StrUtil.isBlank(avatar) && !avatar.contains(QiNiuConfig.QI_NIU_RESOURCE_URL)) {
            systemUser.setAvatar(QiNiuConfig.QI_NIU_RESOURCE_URL + systemUser.getAvatar());
        }
        return systemUser;
    }

    /**
     * 获取登录管理员ID
     *
     * @return
     */
    public static Long getSystemUserId() {
        SystemUser systemUser = getCurrentUser();
        if (systemUser == null) {
            return -1L;
        }
        return systemUser.getId();
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
    public static String createUserSlat() {
        int randomInt = RandomUtil.randomInt(10);
        String sub = IdUtil.randomUUID().substring(4, 11);
        return handleUserPassword(sub).substring(randomInt, randomInt + 6);
    }

    /**
     * 刷新当前用户session
     *
     * @param info
     */
    public static void refreshSession(SystemUser info) {
        SystemUser systemUser = getCurrentUser();
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

    /**
     * 获取本机ip地址
     *
     * @return
     */
    public static String getLocalhostIP() {
        String ip = CommonConstant.NULL_KEY_STR;
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取请求的真实IP地址
     *
     * @param request
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取Cpu使用率
     */
    public static double getCpuUsageInfo() {
        double cpuRatio = 0;
        String osName = getOsName();
        if ("Windows".contains(osName)) {
            return 30;
        }
        try {
            File file = new File(LINUX_CPU_INFO_FILE);
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringTokenizer token = new StringTokenizer(br.readLine());
            token.nextToken();

            long user1 = Long.parseLong(token.nextToken() + "");
            long nice1 = Long.parseLong(token.nextToken() + "");
            long sys1 = Long.parseLong(token.nextToken() + "");
            long idle1 = Long.parseLong(token.nextToken() + "");
            Thread.sleep(1000);

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            token = new StringTokenizer(br.readLine());
            token.nextToken();

            long user2 = Long.parseLong(token.nextToken());
            long nice2 = Long.parseLong(token.nextToken());
            long sys2 = Long.parseLong(token.nextToken());
            long idle2 = Long.parseLong(token.nextToken());
            cpuRatio = (double) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) * 100
                    / (double) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
        } catch (Exception e) {
            log.error("cpu信息获取异常：{}", e.getMessage());
        }
        return Math.round(cpuRatio);
    }

    /**
     * 获取内存使用率
     */
    public static double getMemoryUsageInfo() {
        Map<String, Object> map = new HashMap<String, Object>(16);
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        String osName = getOsName();
        if ("Windows".contains(osName)) {
            return 40;
        }
        try {
            inputs = new InputStreamReader(new FileInputStream(LINUX_MEMORY_INFO_FILE));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                int beginIndex = 0;
                int endIndex = line.indexOf(":");
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace("kB", "").trim();
                    map.put(key, value);
                }
            }
            long memTotal = Long.parseLong(map.get("MemTotal").toString());
            long memFree = Long.parseLong(map.get("MemFree").toString());
            long memused = memTotal - memFree;
            long buffers = Long.parseLong(map.get("Buffers").toString());
            long cached = Long.parseLong(map.get("Cached").toString());
            double usage = (double) (memused - buffers - cached) / memTotal * 100;
            BigDecimal b1 = new BigDecimal(usage);
            double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return Math.round(memoryUsage);
        } catch (Exception e) {
            log.error("获取内存信息失败：{}", e.getMessage());
        } finally {
            try {
                assert buffer != null;
                buffer.close();
                inputs.close();
            } catch (Exception e1) {
                log.error("流关闭失败：{}", e1.getMessage());
            }
        }
        return 0;
    }

    /**
     * Linux 得到磁盘的使用率
     */
    public static double getDiskUsageInfo() throws Exception {
        double totalHD = 0;
        double usedHD = 0;
        String osName = getOsName();
        if ("Windows".contains(osName)) {
            return 50;
        }
        if (osName.contains("Mac")) {
            return 50;
        }
        Runtime rt = Runtime.getRuntime();
        // df -hl 查看硬盘空间
        Process p = rt.exec("df -hl");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = null;
            String[] strArray = null;
            while ((str = in.readLine()) != null) {
                int m = 0;
                strArray = str.split(" ");
                for (String tmp : strArray) {
                    if (tmp.trim().length() == 0) {
                        continue;
                    }
                    ++m;
                    totalHD = Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                    if ("G".contains(tmp)) {
                        if (m == 2) {
                            if (!DEFAULT_ZERO.equals(tmp)) {
                                totalHD += totalHD * 1024;
                            }
                        }
                        if (m == 3) {
                            if (!DEFAULT_ZERO.equals(tmp)) {
                                usedHD += totalHD * 1024;
                            }
                        }
                    }
                    if ("M".contains(tmp)) {
                        if (m == 2) {
                            if (!DEFAULT_ZERO.equals(tmp)) {
                                totalHD += totalHD;
                            }
                        }
                        if (m == 3) {
                            if (!DEFAULT_ZERO.equals(tmp)) {
                                usedHD += totalHD;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug("硬盘信息获取异常：{}", e.getMessage());
        } finally {
            assert in != null;
            in.close();
        }
        // 保留2位小数
        double precent = (usedHD / totalHD) * 100;
        BigDecimal b1 = new BigDecimal(precent);
        precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Math.round(precent);
    }

    /**
     * 生成当前部门的Level
     *
     * @param parenLevel 父级部门的level
     * @param parentId   父级部门ID
     * @return 生成的部门Level, 如0.1.2
     */
    public static String calculateLevel(String parenLevel, Long parentId) {
        if (StrUtil.isBlank(parenLevel)) {
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
     * @return
     */
    public static String getMenuCode(String href, Long menuId) {
        return SecureUtil.md5(CommonConstant.MENU_CODE + href + menuId);
    }

    /**
     * 获取权限code
     *
     * @param permissionCode 菜单权限code
     * @return
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
        if (StrUtil.isBlank(href)) {
            return null;
        }
        List<String> resultUrl = Splitter.on("/").omitEmptyStrings().splitToList(href);
        return Joiner.on(":").join(resultUrl);
    }

}
