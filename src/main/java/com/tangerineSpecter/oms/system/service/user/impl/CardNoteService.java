package com.tangerinespecter.oms.system.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import com.tangerinespecter.oms.system.mapper.UserCardNoteMapper;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CardNoteService implements ICardNoteService {
	
	@Resource
	private UserCardNoteMapper cardNoteMapper;
	
	@Override
	public ServiceResult queryForPage(UserCardNoteQueryObject qo) {
		PageHelper.startPage(qo.getPage(), qo.getLimit());
		List<UserCardNote> userCardNotes = cardNoteMapper.queryForPage(qo);
		return ServiceResult.pageSuccess(new PageInfo<>(userCardNotes));
	}
	
	@Override
	public ServiceResult insert(CardNoteInfoVo vo) {
		UserCardNote cardNote = new UserCardNote();
		cardNote.setContent(vo.getContent());
		cardNote.setAdminId(SystemUtils.getSystemUserId());
		cardNoteMapper.insert(cardNote);
		return ServiceResult.success();
	}
	
	@Override
	public ServiceResult delete(Long id) {
		cardNoteMapper.update(UserCardNote.builder().isDel(1).build(),
				new UpdateWrapper<UserCardNote>().eq("id", id));
		return ServiceResult.success();
	}
}
