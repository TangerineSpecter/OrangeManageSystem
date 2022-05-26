package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 货币汇率响应结果
 *
 * @author TangerineSpecter
 * @desc https://www.juhe.cn/docs/api/id/80
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExchangeRateResponse extends JuheApiBaseResponse<ExchangeRateResponse> implements Serializable {

    @JSONField(name = "list")
    private List<List<String>> list;
    /**
     * 更新时间
     */
    @JSONField(name = "update")
    private String update;

}



