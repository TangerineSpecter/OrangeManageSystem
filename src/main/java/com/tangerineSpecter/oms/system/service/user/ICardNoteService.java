package com.tangerinespecter.oms.system.service.user;

import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagVo;

import java.util.List;

public interface ICardNoteService {
	
	
	ServiceResult queryForPage(UserCardNoteQueryObject qo);
	
	ServiceResult insert(CardNoteInfoVo vo);
	
	ServiceResult delete(Long id);
	
	CardNoteInfoDto noteInfo();
	
	ServiceResult insertTag(CardNoteTagVo vo);
	
	List<CardNoteListVo> randOne();
	
	ServiceResult deleteTag(Long tagId);
	
	ServiceResult restore(Long id);
	
	ServiceResult forceDelete(Long id);
}
