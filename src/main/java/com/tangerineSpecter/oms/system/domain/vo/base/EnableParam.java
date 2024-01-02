package com.tangerinespecter.oms.system.domain.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("启用状态参数模型")
public class EnableParam extends IdParamVo implements Serializable {

    @NotNull(message = "启用状态不能为空")
    @ApiModelProperty("是否启用；true：启用；false：停用")
    private boolean enable;
}
