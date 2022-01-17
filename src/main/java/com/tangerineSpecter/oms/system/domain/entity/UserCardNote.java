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
 * 卡片笔记
 *
 * @author TangerineSpecter
 * @version v0.4.1
 * @Date 2022年01月17日10:57:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_card_note")
public class UserCardNote implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 笔记内容
     */
    private String content;
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
