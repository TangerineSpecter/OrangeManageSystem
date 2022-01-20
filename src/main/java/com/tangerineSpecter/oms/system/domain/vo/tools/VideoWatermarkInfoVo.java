package com.tangerinespecter.oms.system.domain.vo.tools;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 视频水印信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoWatermarkInfoVo {
	
	/**
	 * 视频地址
	 */
	@NotBlank(message = "视频地址不能为空")
	@ApiModelProperty("视频地址")
	private String content;
}
