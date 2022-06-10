package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.entity.UserNoteTagAssoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collections;
import java.util.List;

@Mapper
public interface UserNoteTagAssocMapper extends BaseMapper<UserNoteTagAssoc> {

    /**
     * 根据标签id清理关联关系
     *
     * @param tagId 标签id
     * @return 关系数量
     */
    default int deleteByTagId(Long tagId) {
        return delete(new QueryWrapper<UserNoteTagAssoc>()
                .eq("tag_id", tagId));
    }

    /**
     * 根据笔记id清理关联关系
     *
     * @param noteId 笔记id
     * @return 关系数量
     */
    default int deleteByNoteId(Long noteId) {
        return delete(new QueryWrapper<UserNoteTagAssoc>()
                .eq("note_id", noteId));
    }

    /**
     * 批量插入标签笔记关联
     *
     * @param noteId 笔记id
     * @param tagIds 标签id
     */
    void batchInsert(@Param("noteId") Long noteId, @Param("tagIds") List<Long> tagIds);

    /**
     * 根据笔记id获取标签id列表
     *
     * @param noteId 笔记id
     * @return 标签id列表
     */
    default List<Long> selectTagIdsByNoteId(Long noteId) {
        if (noteId == null) {
            return Collections.emptyList();
        }
        List<UserNoteTagAssoc> list = selectList(new QueryWrapper<UserNoteTagAssoc>()
                .eq("note_id", noteId));
        return CollUtils.convertList(list, UserNoteTagAssoc::getTagId);
    }
}