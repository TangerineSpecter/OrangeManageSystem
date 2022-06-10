package com.tangerinespecter.oms.system.domain.vo.user;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 卡片笔记标签关联参数
 *
 * @author 丢失的橘子
 * @date 2022年06月10日23:34:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardNoteTagAssocVo implements Serializable {

    @NotEmpty(groups = {Update.class}, message = "笔记id不能为空")
    @ApiModelProperty("笔记id")
    private Long id;
    @ApiModelProperty("关联的标签id，逗号间隔，无则为清空")
    private String noteTag;
}
