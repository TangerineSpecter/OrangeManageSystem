package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tangerinespecter.oms.common.enums.LogOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统日志表
 *
 * @author TangerineSpecter
 * @date 2019年09月04日10:04:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 操作管理员
     */
    private String username;
    /**
     * 操作事件
     */
    private Integer event;
    /**
     * 操作方法
     */
    private String method;
    /**
     * 操作参数
     */
    private String params;
    /**
     * 操作内容
     */
    private String operation;
    /**
     * 操作耗时（单位：毫秒）
     */
    private Long time;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 操作时间
     */
    private String createTime;
}
