package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.domain.vo.base.IdParamVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 丢失的橘子
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("定时任务模型")
public class SystemScheduledVo extends IdParamVo implements Serializable {

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = "任务名称不能为空")
    private String name;
    @ApiModelProperty(value = "时间表达式", required = true)
    @NotBlank(message = "时间表达式不能为空")
    private String cron;
    @ApiModelProperty(value = "类型（0：一般调度，1：机器人通知）", required = true)
    private Integer type;
    //    @NotBlank(message = "任务类名不能为空")
    @ApiModelProperty("任务类名")
    private String classPath;
    @ApiModelProperty("任务描述")
    private String description;
    @ApiModelProperty("机器人id")
    private Long botId;
}
