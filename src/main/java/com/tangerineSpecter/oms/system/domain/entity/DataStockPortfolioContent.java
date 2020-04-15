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
public class DataStockPortfolioContent implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 股票ID
     */
    private Long stockId;
    /**
     * 组合ID
     */
    private Long portfolioId;
    /**
     * 信息
     */
    private String information;
}
