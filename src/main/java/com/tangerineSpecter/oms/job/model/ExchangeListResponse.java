package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tangerinespecter.oms.system.domain.entity.DataExchange;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 货币列表响应结果
 *
 * @author TangerineSpecter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExchangeListResponse extends JuheApiBaseResponse<ExchangeListResponse> implements Serializable {

    @JSONField(name = "list")
    private List<DataExchange> list;
}



