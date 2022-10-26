package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 食物数据页面高级查询
 *
 * @author 丢失的橘子
 * @version v0.5.1
 * @Date 2022年10月26日17:50:00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLibraryQueryObject implements Serializable {

    @ApiModelProperty("名称")
    private String name;
}
