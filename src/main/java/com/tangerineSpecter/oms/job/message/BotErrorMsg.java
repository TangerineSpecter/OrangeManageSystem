package com.tangerinespecter.oms.job.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 机器人告警通知
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotErrorMsg implements IBaseMessage, Serializable {

    @ApiModelProperty("通知标题")
    private String title = "服务异常告警";
    @ApiModelProperty("通知内容")
    private String content;
}
