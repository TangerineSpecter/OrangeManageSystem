package com.tangerinespecter.oms.system.domain.vo.user;

import com.tangerinespecter.oms.system.valid.Insert;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 卡片笔记标签参数
 *
 * @author 丢失的橘子
 * @date 2022年1月19日 00:29:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardNoteTagVo implements Serializable {

    @NotEmpty(groups = {Update.class}, message = "笔记id不能为空")
    @ApiModelProperty("笔记id")
    private Long id;
    @NotBlank(message = "标签名称不能为空", groups = Insert.class)
    @ApiModelProperty("笔记标签名称")
    private String name;
    @ApiModelProperty("关联的标签id，逗号间隔")
    private String noteTag;
}
