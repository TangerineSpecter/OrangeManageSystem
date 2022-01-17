package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCardNoteMapper extends BaseMapper<UserCardNote> {
	
	
	List<UserCardNote> queryForPage(UserCardNoteQueryObject qo);
	
}