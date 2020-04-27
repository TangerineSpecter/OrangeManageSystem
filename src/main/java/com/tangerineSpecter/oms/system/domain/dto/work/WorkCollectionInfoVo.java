package com.tangerinespecter.oms.system.domain.dto.work;

import com.tangerinespecter.oms.common.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkCollectionInfoVo {

    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;

    private String remark;
    @NotNull(message = "类型不能为空")
    private Integer type;

    private Integer sort = CommonConstant.Number.COMMON_NUMBER_ZERO;

}
