package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 基金历史数据
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundHistoryData implements Serializable {

    @JSONField(name = "date")
    private LocalDateTime date;
    @JSONField(name = "nav")
    private BigDecimal nav;
    @JSONField(name = "percentage")
    private BigDecimal percentage;
    @JSONField(name = "value")
    private BigDecimal value;
}
