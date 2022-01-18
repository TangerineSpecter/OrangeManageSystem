package com.tangerinespecter.oms.system.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("卡片笔记提交记录信息")
public class CardNoteSubmitInfo {
	
	@ApiModelProperty("记录日期")
	private String noteDay;
	@ApiModelProperty("记录次数")
	private Integer count;
}
