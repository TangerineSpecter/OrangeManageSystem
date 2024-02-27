package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.domain.vo.base.IdParamVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 丢失的橘子
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("定时任务执行模型")
public class ExecuteJobVo extends IdParamVo implements Serializable {

    @ApiModelProperty(value = "执行参数，不填则采用默认")
    private String param;
}
