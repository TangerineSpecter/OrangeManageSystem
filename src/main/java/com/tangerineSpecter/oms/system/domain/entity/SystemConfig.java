package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("web标题")
    @TableField("web_title")
    private String webTitle;
    @ApiModelProperty("webUrl")
    @TableField("web_url")
    private String webUrl;
    @ApiModelProperty("缓存时间")
    @TableField("cache_time")
    private Integer cacheTime;
    @ApiModelProperty("文件大小")
    @TableField("file_size")
    private Long fileSize;
    @ApiModelProperty("主页标题")
    @TableField("home_title")
    private String homeTitle;
    @ApiModelProperty("上传格式限制")
    @TableField("file_suffix")
    private String fileSuffix;
    @ApiModelProperty("版权信息")
    @TableField("copyright")
    private String copyright;
    @ApiModelProperty("告警推送机器人地址，目前支持（飞书）")
    @TableField("error_webhook")
    private String errorWebhook;
}
