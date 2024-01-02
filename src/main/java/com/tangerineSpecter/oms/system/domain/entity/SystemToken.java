package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 令牌记录表
 * </p>
 *
 * @author 丢失的橘子
 * @since 2023-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_token")
@ApiModel(value = "SystemToken对象", description = "令牌记录表")
public class SystemToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称描述")
    private String name;

    @ApiModelProperty("webhook地址")
    private String webhook;

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("平台，0：飞书")
    private Integer platform;

    @ApiModelProperty("类型，0：机器人")
    private Integer type;

    @ApiModelProperty("消息体")
    private String messageInfo;

    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
