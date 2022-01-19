package com.tangerinespecter.oms.system.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("笔记标签基本信息")
public class NoteTagSimpleInfo {

    @ApiModelProperty("标签id")
    private Long id;
    @ApiModelProperty("标签名称")
    private String name;
}
