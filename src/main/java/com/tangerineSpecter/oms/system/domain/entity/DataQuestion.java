package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("问题")
    @TableField("question")
    private String question;
    @ApiModelProperty("回答内容")
    @TableField("content")
    private String content;
    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    @TableField("is_del")
    private Integer isDel;
}
