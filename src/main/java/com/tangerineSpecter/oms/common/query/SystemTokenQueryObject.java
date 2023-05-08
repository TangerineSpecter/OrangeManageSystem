package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统令牌高级查询
 *
 * @author 丢失的橘子
 * @version 0.5.2
 * @date 2023年05月08日11:35:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemTokenQueryObject implements Serializable {

    @ApiModelProperty("描述名称")
    private String name;
    @ApiModelProperty("类型，0：通知机器人")
    private Integer type;
}
