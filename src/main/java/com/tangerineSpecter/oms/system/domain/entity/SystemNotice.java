package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统消息表
 *
 * @author 丢失的橘子
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_notice")
public class SystemNotice implements Serializable {

    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("消息标题")
    private String title;
    @ApiModelProperty("消息内容")
    private String content;
    @ApiModelProperty("消息类型")
    private Integer type;
    @ApiModelProperty("推送状态（0：未推送；1：已推送）")
    private Integer pushStatus;
    @ApiModelProperty("阅读状态（0：未读；1：已读）")
    private Integer readStatus;
    @JsonIgnore
    @ApiModelProperty("管理员ID")
    private String uid;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;
}
