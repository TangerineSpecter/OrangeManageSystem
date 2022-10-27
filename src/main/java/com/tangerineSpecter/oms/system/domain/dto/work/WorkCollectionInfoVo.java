package com.tangerinespecter.oms.system.domain.dto.work;

import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkCollectionInfoVo {

    @ApiModelProperty("收藏id，更新必传")
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long id;
    @ApiModelProperty("收藏标题")
    @NotBlank(message = "标题不能为空")
    private String title;
    @ApiModelProperty("收藏内容")
    @NotBlank(message = "内容不能为空")
    private String content;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("类型")
    @NotNull(message = "类型不能为空")
    private Integer type;
    @ApiModelProperty("排序")
    private Integer sort = CommonConstant.Number.COMMON_NUMBER_ZERO;

}
