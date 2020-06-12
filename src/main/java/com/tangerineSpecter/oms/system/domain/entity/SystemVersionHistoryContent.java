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
 * 系统版本历史内容
 *
 * @author TangerineSpecter
 * @version v0.4.0
 * @Date 2020年05月27日10:27:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_version_history_content")
public class SystemVersionHistoryContent implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 版本ID
     */
    private Long versionId;
    /**
     * 功能类型（0：新增；1：优化；2：改善；3：修复；4：重构）
     */
    private Integer type;
    /**
     * 更新内容
     */
    private String content;
}
