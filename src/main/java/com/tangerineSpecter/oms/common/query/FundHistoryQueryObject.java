package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 基金历史数据高级查询
 *
 * @author TangerineSpecter
 * @version v0.5.1
 * @date 2022年10月16日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundHistoryQueryObject implements Serializable {

    @NotNull(message = "基金代码不能为空")
    @ApiModelProperty("基金代码")
    private String code;
}
