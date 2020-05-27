package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统历史版本
 *
 * @author TangerineSpecter
 * @version v0.4.0
 * @Date 2020年05月27日10:27:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_version_history")
public class SystemVersionHistory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 版本号
     */
    private String version;
    /**
     * 版本号数字
     */
    private Integer versionNumber;
    /**
     * 创建时间
     */
    private String createTime;
}
