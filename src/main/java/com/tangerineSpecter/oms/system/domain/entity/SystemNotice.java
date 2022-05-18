package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统消息表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemNotice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 推送状态（0：未推送；1：已推送）
     */
    private Integer pushStatus;
    /**
     * 阅读状态（0：未读；1：已读）
     */
    private Integer readStatus;
    /**
     * 管理员ID
     */
    private String uid;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
