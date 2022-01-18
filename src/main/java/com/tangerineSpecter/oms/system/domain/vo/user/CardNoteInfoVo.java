package com.tangerinespecter.oms.system.domain.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 卡片笔记参数
 *
 * @author 丢失的橘子
 * @date 2022年01月17日10:36:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardNoteInfoVo implements Serializable {
	
	@NotBlank(message = "笔记内容不能为空")
	@ApiModelProperty("笔记内容")
	private String content;
}
