package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 丢失的橘子
 * @version 0.4.1
 * @date 2022年1月6日 01:53:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_wall_page")
public class DataWallPage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 开始时间
     */
    @TableField("start_date")
    private Long startDate;
    /**
     * 结束时间
     */
    @TableField("end_date")
    private Long endDate;
    /**
     * 壁纸url
     */
    @TableField("url")
    private String url;
    /**
     * 完整开始时间
     */
    @TableField("full_start_date")
    private Long fullStartDate;
    /**
     * 跳转搜索链接
     */
    @TableField("copyright_link")
    private String copyrightLink;
    /**
     * 版权信息
     */
    @TableField("copyright")
    private String copyright;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * hash值
     */
    @TableField("hash")
    private String hash;
}
