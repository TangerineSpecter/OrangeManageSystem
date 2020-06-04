package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateStatusVo {

    private String ids;
    /**
     * 阅读状态（0：未读；1：已读）
     */
    private Integer readStatus;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
}
