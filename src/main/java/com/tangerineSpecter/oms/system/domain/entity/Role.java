package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 *
 * @author TangerineSpecter
 * @date 2019年09月16日01:11:57
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Set<Permission> permissions = new HashSet<>();
}
