package com.tangerinespecter.oms.common.config;

import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云cos资源配置
 *
 * @author 丢失的橘子
 * @date 2022年08月30日11:13:39
 */
@Getter
@Configuration
public class CosConfig {

    @Value("${tencent.cos.secretId}")
    private String secretId;
    @Value("${tencent.cos.secretKey}")
    private String secretKey;
    @Value("${tencent.cos.bucketName}")
    private String bucketName;
    @Value("${tencent.cos.bucketRegion}")
    private String bucketRegion;
    @Value("${tencent.cos.bucketPath}")
    private String bucketPath;

    /**
     * 头像存储空间
     */
    public static final String AVATAR_ZONE = "avatar-zone/";

    /**
     * 组装头像地址
     *
     * @param systemUser 头像
     * @return 头像地址
     */
    public void initAvatar(SystemUser systemUser) {
        systemUser.setAvatar(this.bucketPath + CosConfig.AVATAR_ZONE + systemUser.getAvatar());
    }
}
