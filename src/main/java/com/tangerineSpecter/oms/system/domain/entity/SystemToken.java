package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("类型，1：通知机器人")
    private Integer type;

    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    public SystemToken(String name, String token, Integer type) {
        this.name = name;
        this.token = token;
        this.type = type;
    }

    public SystemToken(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }
}
