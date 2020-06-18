package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 收藏表
 *
 * @author TangerineSpecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkCollection implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 类型（0：网站；1：学习；2：交易）
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 删除状态
     */
    private Integer isDel;

}