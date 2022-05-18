package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteSubmitInfo;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserCardNoteMapper extends BaseMapper<UserCardNote> {
	
	
	List<CardNoteListVo> queryForPage(UserCardNoteQueryObject qo);
	
	/**
	 * 获取笔记聚合信息
	 *
	 * @param uid 管理员id
	 * @return 提交记录信息
	 */
	List<CardNoteSubmitInfo> selectListSubmitInfo(@Param("uid") String uid);
	
	/**
	 * 随机一篇笔记
	 *
	 * @param uid 管理员id
	 * @return 笔记内容
	 */
	List<CardNoteListVo> randOne(@Param("uid") String uid);
	
	/**
	 * 物理删除笔记
	 *
	 * @param id 笔记id
	 */
	void forceDeleteById(@Param("id") Long id);
	
	/**
	 * 恢复笔记
	 *
	 * @param id 笔记id
	 * @return 更新条数
	 */
	int restoreNoteById(@Param("id") Long id);
}