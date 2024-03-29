package com.tangerinespecter.oms.system.domain.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 丢失的橘子
 * @date 2023年05月09日14:13:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_scheduled_task")
@ApiModel(value = "SystemScheduledTask对象", description = "定时任务表")
public class SystemScheduledTask implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("任务完整类名")
    private String classPath;
    @ApiModelProperty("任务名称")
    private String name;
    @ApiModelProperty("时间表达式")
    private String cron;
    @ApiModelProperty("类型（0：系统默认，1：一般调度，2：机器人通知）")
    private Integer type;
    @ApiModelProperty("消息类型：0：简单消息")
    private Integer msgType;
    @ApiModelProperty("类型对应的额外信息，比如，绑定的机器人id")
    private String extraInfo;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("状态，0：停用；1：启用")
    private Integer status;
    @ApiModelProperty("任务执行结果，0：失败；1：成功")
    private Integer result;
    @ApiModelProperty("执行耗时，单位：秒")
    private String timestamp;
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
