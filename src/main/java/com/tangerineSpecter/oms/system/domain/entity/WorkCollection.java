package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏表
 *
 * @author TangerineSpecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkCollection {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String remark;

    private Integer type;

    private Integer sort;

    private Integer isDel;

}