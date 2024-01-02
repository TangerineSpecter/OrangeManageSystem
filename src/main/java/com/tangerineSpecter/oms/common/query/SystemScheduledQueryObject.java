package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统定时任务高级查询
 *
 * @author 丢失的橘子
 * @version 0.5.2
 * @date 2023年05月09日14:56:55
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemScheduledQueryObject implements Serializable {

    @ApiModelProperty("任务名称")
    private String name;
}
