package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统权限表
 *
 * @author TangerineSpecter
 * @date 2019年09月16日01:11:57
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_permission")
public class SystemPermission {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限请求url
     */
    private String url;
    /**
     * 权限码
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序（从大到小）
     */
    private Integer sort;
}
