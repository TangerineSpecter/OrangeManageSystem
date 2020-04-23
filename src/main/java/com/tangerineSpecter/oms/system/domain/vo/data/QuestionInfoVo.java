package com.tangerinespecter.oms.system.domain.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoVo {

    private Long id;
    @NotBlank(message = "问题不能为空")
    private String question;
    @NotBlank(message = "回复不能为空")
    private String content;
}
