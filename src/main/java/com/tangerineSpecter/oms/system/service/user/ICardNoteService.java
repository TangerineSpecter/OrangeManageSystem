package com.tangerinespecter.oms.system.service.user;

import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;

public interface ICardNoteService {
	
	
	ServiceResult queryForPage(UserCardNoteQueryObject qo);
	
	ServiceResult insert(CardNoteInfoVo vo);
	
	ServiceResult delete(Long id);
	
	CardNoteInfoDto noteInfo();
}
