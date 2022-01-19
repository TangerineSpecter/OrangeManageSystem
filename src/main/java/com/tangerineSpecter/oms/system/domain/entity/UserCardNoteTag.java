package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 卡片笔记标签表
 *
 * @author TangerineSpecter
 * @version v0.4.1
 * @Date 2022年01月17日10:57:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_card_note_tag")
public class UserCardNoteTag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 置顶状态
     */
    private Integer top;
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
