package com.tangerinespecter.oms.system.domain.dto.system;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
