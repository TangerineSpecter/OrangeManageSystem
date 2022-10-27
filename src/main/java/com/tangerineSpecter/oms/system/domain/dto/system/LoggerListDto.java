package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.common.enums.LogOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统日志列表
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggerListDto implements Serializable {

    private Long id;
    @ApiModelProperty("操作人")
    private String username;
    @ApiModelProperty("操作事件")
    private Integer event;
    @ApiModelProperty("事件描述")
    private String eventDesc;
    @ApiModelProperty("事件方法")
    private String method;
    @ApiModelProperty("事件参数")
    private String params;
    @ApiModelProperty("事件内容")
    private String operation;
    @ApiModelProperty("事件耗时")
    private Long time;
    @ApiModelProperty("IP地址")
    private String ip;
    @ApiModelProperty("操作时间")
    private String createTime;

    public void setEvent(Integer event) {
        this.event = event;
        this.eventDesc = LogOperation.getDesc(event);
    }
}
