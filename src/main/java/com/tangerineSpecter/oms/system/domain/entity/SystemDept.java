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
 * 系统部门表
 *
 * @author TangerineSpecter
 * @date 2020年02月15日20:31:40
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_dept")
public class SystemDept {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门层级
     */
    private String level;
    /**
     * 部门备注
     */
    private String remark;
    /**
     * 部门父级id
     */
    private Long parentId;
    /**
     * 排序（从大到小）
     */
    private Integer sort;

}
