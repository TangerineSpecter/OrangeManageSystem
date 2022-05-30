package com.tangerinespecter.oms.common.context;

import com.tangerinespecter.oms.common.config.QiNiuConfig;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;

import java.util.Optional;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {

    private String uid;

    private String nickName;

    private String userName;

    private String phoneNumber;

    private String city;

    private String email;

    private String avatar;

    private String brief;

    private Integer sex;

    private String birthday;

    public static SystemUser getCurrentUser() {
        try {
            return Optional.ofNullable((SystemUser) SecurityUtils.getSubject().getPrincipal()).orElse(new SystemUser());
        } catch (Exception ignored) {
            return new SystemUser();
        }
    }

    public static String getUid() {
        return getCurrentUser().getUid();
    }

    public static String getNickName() {
        return getCurrentUser().getNickName();
    }

    public static String getUserName() {
        return getCurrentUser().getUsername();
    }

    public static String getPhoneNumber() {
        return getCurrentUser().getPhoneNumber();
    }

    public static String getCity() {
        return getCurrentUser().getCity();
    }

    public static String getEmail() {
        return getCurrentUser().getEmail();
    }

    public static String getAvatar() {
        return QiNiuConfig.QI_NIU_RESOURCE_URL + getCurrentUser().getAvatar();
    }

    public String getBrief() {
        return getCurrentUser().getBrief();
    }

    public static Integer getSex() {
        return getCurrentUser().getSex();
    }

    public static String getBirthday() {
        return getCurrentUser().getBirthday();
    }
}
