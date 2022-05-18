package com.tangerinespecter.oms.system.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardNoteListVo {

    private Long id;
    /**
     * 笔记内容
     */
    private String content;
    /**
     * 管理员id
     */
    private String uid;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Integer isDel;
    /**
     * 笔记标签
     */
    private List<NoteTagSimpleInfo> tags;
}
