package com.tangerinespecter.oms.system.domain.vo.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Nlp分析内容信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NlpInfoVo {

    @ApiModelProperty("关键词")
    private List<String> keyword;

    @ApiModelProperty("摘要")
    private List<String> summary;

    @ApiModelProperty("词频统计")
    private Map<String, Integer> wordMap;
}
