package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 股票表
 *
 * @author tangerineSpecter
 * @date 2020年04月14日10:12:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataStock {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 市场
     */
    private String market;
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票完整代码
     */
    private String fullCode;
    /**
     * 上市时间
     */
    private String listingDate;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 流通股本（万股）
     */
    private BigDecimal currCapital;
    /**
     * 四季度净利润
     */
    private BigDecimal profitFour;
    /**
     * 总股本
     */
    private BigDecimal totalCapital;
    /**
     * 每股净资产
     */
    private BigDecimal netPerAssets;
    /**
     * 股票拼音
     */
    private String pinyin;
    /**
     * 股票状态 1： 上市， 其他停牌
     */
    private Integer state;
    /**
     * 行业ID
     */
    private Long industryId;
    /**
     * 归属母公司股东净利润
     */
    private BigDecimal netIncome;
}
