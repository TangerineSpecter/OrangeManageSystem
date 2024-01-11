package com.tangerinespecter.oms.system.domain.vo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigInfoVo {

    @ApiModelProperty("网页标题")
    private String webTitle;
    @ApiModelProperty("网站地址")
    private String webUrl;
    @ApiModelProperty("全局缓存时间，单位（秒）")
    private Integer cacheTime;
    @ApiModelProperty("文件上传大小限制，单位（KB）")
    private Long fileSize;
    @ApiModelProperty("上传文件后缀限制")
    private String fileSuffix;
    @ApiModelProperty("首页标题")
    private String homeTitle;
    @ApiModelProperty("版权申明")
    private String copyright;
    @ApiModelProperty("告警推送机器人地址")
    private String errorWebhook;
    @ApiModelProperty("告警推送开关，0：关闭；1：开启")
    private Integer errorEnable;
}
