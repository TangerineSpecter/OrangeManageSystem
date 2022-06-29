package com.tangerinespecter.oms.system.domain.dto.user;

import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import com.tangerinespecter.oms.system.domain.vo.user.NoteTagSimpleInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 卡片笔记信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("卡片笔记信息")
public class CardNoteInfoDto {

    @ApiModelProperty("笔记数量")
    private int noteCount;
    @ApiModelProperty("标签数量")
    private long tagCount;
    @ApiModelProperty("记录天数")
    private int day;
    @ApiModelProperty("提交记录信息")
    private List<CardNoteSubmitInfo> submitInfos;
    @ApiModelProperty("全部标签")
    private List<UserCardNoteTag> allTags;
    @ApiModelProperty("置顶标签")
    private List<UserCardNoteTag> topTags;
}
