package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基金页面高级查询
 *
 * @author TangerineSpecter
 * @version v0.5.1
 * @date 2022年10月16日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundQueryObject implements Serializable {

    @ApiModelProperty("关键词：基金名称、代码")
    private String keyword;
}
