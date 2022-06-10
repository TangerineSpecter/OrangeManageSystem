package com.tangerinespecter.oms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 七牛配置文件
 *
 * @author TangerineSpecter
 */
@Configuration
public class QiNiuConfig {

    /**
     * 资源地址
     */
    public static String QI_NIU_RESOURCE_URL;
    /**
     * 七牛Access秘钥
     */
    public static String QI_NIU_ACCESS_KEY;
    /**
     * 七牛Secret秘钥
     */
    public static String QI_NIU_SECRET_KEY;
    /**
     * 头像存储空间
     */
    public static String AVATAR_ZONE = "avatar-zone";

    @Value("${qi.niu.resource.url}")
    public void setQiNiuResourceUrl(String qiNiuResourceUrl) {
        QiNiuConfig.QI_NIU_RESOURCE_URL = qiNiuResourceUrl;
    }

    @Value("${qi.niu.access.key}")
    public void setQiNiuAccessKey(String qiNiuAccessKey) {
        QiNiuConfig.QI_NIU_ACCESS_KEY = qiNiuAccessKey;
    }

    @Value("${qi.niu.secret.key}")
    public void setQiNiuSecretKey(String qiNiuSecretKey) {
        QiNiuConfig.QI_NIU_SECRET_KEY = qiNiuSecretKey;
    }
}
