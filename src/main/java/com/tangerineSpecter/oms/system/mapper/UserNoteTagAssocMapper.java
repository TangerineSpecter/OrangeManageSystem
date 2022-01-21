package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.UserNoteTagAssoc;
import org.apache.ibatis.annotations.Mapper;

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
}