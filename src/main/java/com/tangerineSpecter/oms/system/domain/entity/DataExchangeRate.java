package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 货币汇率表
 *
 * @author TangerineSpecter
 * @date 2022年05月27日00:08:22
 */
@Data
@Alias("DataExchangeRate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_exchange_rate")
@EqualsAndHashCode(callSuper = true)
public class DataExchangeRate extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 源货币名称
     */
    @TableField("name")
    private String name;
    /**
     * 源货币代码
     */
    @TableField("code")
    private String code;
    /**
     * 每100源货币的RMB兑换值
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 汇率记录时间
     */
    @TableField("record_time")
    private LocalDateTime recordTime;
}



