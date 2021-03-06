package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统公告表
 *
 * @author TengerineSpecter
 * @version 0.1.1
 * @date 2020年04月21日15:19:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemBulletin {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否置顶（0：否；1：是）
     */
    private Integer top;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;

}
