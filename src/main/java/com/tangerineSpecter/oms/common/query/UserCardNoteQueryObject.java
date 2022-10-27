package com.tangerinespecter.oms.common.query;

import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 卡片笔记页面高级查询
 *
 * @author TangerineSpecter
 * @version 0.5.0
 * @date 2022年01月17日11:28:15
 */
@Getter
@Setter
@AllArgsConstructor
@ApiModel("卡片笔记搜索参数")
public class UserCardNoteQueryObject extends BaseQueryObject {

    @ApiModelProperty("搜索关键词")
    private String keyword;
    @ApiModelProperty("标签id")
    private Long tagId;
    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;

    public UserCardNoteQueryObject() {
        super();
        this.isDel = GlobalBoolEnum.FALSE.getValue();
    }
}
