package com.tangerinespecter.oms.system.domain.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
	
	@NotBlank(message = "标签名称不能为空")
	@ApiModelProperty("笔记标签名称")
	private String name;
}
