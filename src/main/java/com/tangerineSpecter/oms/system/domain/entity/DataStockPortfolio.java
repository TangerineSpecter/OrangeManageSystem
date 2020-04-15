package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 股票组合表
 *
 * @author TangerineSpecter
 * @version v0.0.7
 * @Date 2020年04月15日14:14:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataStockPortfolio implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
