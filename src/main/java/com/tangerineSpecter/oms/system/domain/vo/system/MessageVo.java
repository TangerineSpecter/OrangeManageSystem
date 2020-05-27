package com.tangerinespecter.oms.system.domain.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    /**
     * 推送人
     */
    @NotNull(message = "消息推送人不能为空")
    private Long uid;
    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空")
    private String title;
    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;
}
