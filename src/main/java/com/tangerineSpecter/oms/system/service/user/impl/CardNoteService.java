package com.tangerinespecter.oms.system.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteSubmitInfo;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.mapper.UserCardNoteMapper;
import com.tangerinespecter.oms.system.mapper.UserCardNoteTagMapper;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
import org.springframework.stereotype.Service;
import sun.plugin2.util.SystemUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CardNoteService implements ICardNoteService {
	
	@Resource
	private UserCardNoteMapper cardNoteMapper;
	@Resource
	private UserCardNoteTagMapper cardNoteTagMapper;
	
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
	
	@Override
	public CardNoteInfoDto noteInfo() {
		CardNoteInfoDto result = new CardNoteInfoDto();
		List<CardNoteSubmitInfo> submitInfos = cardNoteMapper.selectListSubmitInfo(SystemUtils.getSystemUserId());
		result.setSubmitInfos(submitInfos);
		result.setDay(submitInfos.size());
		result.setNoteCount(submitInfos.stream().mapToInt(CardNoteSubmitInfo::getCount).sum());
		result.setTagCount(cardNoteTagMapper.selectCountByAdminId(SystemUtils.getSystemUserId()));
		return result;
	}
}
