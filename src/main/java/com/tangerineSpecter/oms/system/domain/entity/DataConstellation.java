package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 星座运势表
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月7日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataConstellation implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 星座名称
     */
    private String name;
    /**
     * 时间（中文）
     */
    private String datetime;
    /**
     * 时间（数字）
     */
    private Integer date;
    /**
     * 综合指数
     */
    private String allLuck;
    /**
     * 幸运色
     */
    private String color;
    /**
     * 健康指数
     */
    private String health;
    /**
     * 爱情指数
     */
    private String love;
    /**
     * 财运指数
     */
    private String money;
    /**
     * 幸运数字
     */
    private Integer number;
    /**
     * 速配星座
     */
    private String Qfriend;

    /**
     * 今日概述
     */
    private String summary;
    /**
     * 工作指数
     */
    private String workLuck;
    /**
     * 创建时间
     */
    private String createTime;
}
