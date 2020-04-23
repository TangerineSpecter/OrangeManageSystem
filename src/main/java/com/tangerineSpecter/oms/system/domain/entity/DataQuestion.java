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
 * 问题表
 *
 * @author TangerineSpecter
 * @version v0.1.1
 * @Date 2020年04月24日00:24:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_question")
public class DataQuestion implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 问题
     */
    private String question;
    /**
     * 回答内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
