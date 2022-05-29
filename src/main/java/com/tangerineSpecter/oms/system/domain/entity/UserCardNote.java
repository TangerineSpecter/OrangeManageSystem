package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 卡片笔记
 *
 * @author TangerineSpecter
 * @version v0.5.0
 * @Date 2022年01月17日10:57:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_card_note")
@EqualsAndHashCode(callSuper = true)
public class UserCardNote extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("笔记内容")
    private String content;
    @ApiModelProperty("创建人")
    @TableField(value = "uid", fill = FieldFill.INSERT)
    private String uid;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;
}
