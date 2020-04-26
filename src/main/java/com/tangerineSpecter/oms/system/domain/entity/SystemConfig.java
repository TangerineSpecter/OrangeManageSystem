package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置表
 *
 * @author tangerineSpecter
 * @date 2020年04月15日20:44:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfig {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * web标题
     */
    private String webTitle;
    /**
     * webUrl
     */
    private String webUrl;
    /**
     * 缓存时间
     */
    private Integer cacheTime;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 主页标题
     */
    private String homeTitle;
    /**
     * 上传格式限制
     */
    private String fileSuffix;
    /**
     * 版权信息
     */
    private String copyright;
}
