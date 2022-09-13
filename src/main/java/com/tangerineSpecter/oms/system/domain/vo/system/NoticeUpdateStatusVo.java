package com.tangerinespecter.oms.system.domain.vo.system;

import com.tangerinespecter.oms.system.valid.Delete;
import com.tangerinespecter.oms.system.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeUpdateStatusVo {

    @NotEmpty(message = "id不能为空", groups = {Update.class, Delete.class})
    private String ids;
    /**
     * 阅读状态（0：未读；1：已读）
     */
    @NotNull(message = "阅读状态不能为空", groups = {Update.class})
    private Integer readStatus;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    @NotNull(message = "删除状态不能为空", groups = {Delete.class})
    private Integer isDel;
}
