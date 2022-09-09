package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tangerinespecter.oms.common.constants.MessageConstant;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.enums.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

/**
 * 消息模型
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("children")
    private List<ChildrenDto> children;

    public void setChildren(List<ChildrenDto> children) {
        if (MessageEnum.ALL_NOTICE.getValue().equals(this.id)) {
            this.children = children;
        }
        //未读消息
        if (MessageEnum.NOT_READ_NOTICE.getValue().equals(this.id)) {
            this.children = CollUtils.filterList(children, message -> MessageConstant.NOT_READ.equals(message.getReadStatus()));
        }
        //系统消息
        if (MessageEnum.SYSTEM_NOTICE.getValue().equals(this.id)) {
            this.children = CollUtils.filterList(children, message -> MessageConstant.SYSTEM_NOTICE.equals(message.getType()));
        }
    }

    @Data
    @NoArgsConstructor
    public static class ChildrenDto implements Serializable {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("title")
        private String title;
        @JsonProperty("context")
        private String context;
        @JsonProperty("form")
        private String form;
        @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
        @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
        @JsonProperty("time")
        private String time;
        private Integer readStatus;
        private Integer type;
    }
}
