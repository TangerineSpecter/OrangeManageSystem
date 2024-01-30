package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 丢失的橘子
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminEntity extends BaseEntity {

    /**
     * 通过EntityInterceptor自定义填充
     */
    @ApiModelProperty("创建人")
    @TableField(value = "uid", fill = FieldFill.INSERT)
    private String uid;
}

