package com.tangerinespecter.oms.system.domain.vo.data;

import com.tangerinespecter.oms.system.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoVo {

    @NotNull(message = "id不能为null", groups = {Update.class})
    private Long id;
    @NotBlank(message = "问题不能为空")
    private String question;
    @NotBlank(message = "回复不能为空")
    private String content;
}
