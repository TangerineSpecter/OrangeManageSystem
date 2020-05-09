package com.tangerinespecter.oms.system.domain.vo.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsFuturesInfoVo {

    /**
     * 保证金
     */
    @NotNull(message = "保证金不能为空")
    @Min(value = 1, message = "保证金不能小于0")
    private String earnestMoney;
    /**
     * 手数
     */
    @NotNull(message = "手数不能为空")
    @Min(value = 1, message = "手数最少为1手")
    private Integer lots;
}
