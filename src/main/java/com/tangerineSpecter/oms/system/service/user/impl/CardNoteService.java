package com.tangerinespecter.oms.system.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteSubmitInfo;
import com.tangerinespecter.oms.system.domain.entity.UserCardNote;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagVo;
import com.tangerinespecter.oms.system.mapper.UserCardNoteMapper;
import com.tangerinespecter.oms.system.mapper.UserCardNoteTagMapper;
import com.tangerinespecter.oms.system.mapper.UserNoteTagAssocMapper;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardNoteService implements ICardNoteService {
	
	@Resource
	private UserCardNoteMapper cardNoteMapper;
	@Resource
	private UserCardNoteTagMapper cardNoteTagMapper;
	@Resource
	private UserNoteTagAssocMapper noteTagAssocMapper;
	
	@Override
	public ServiceResult queryForPage(UserCardNoteQueryObject qo) {
		PageHelper.startPage(qo.getPage(), qo.getLimit());
		List<CardNoteListVo> userCardNotes = cardNoteMapper.queryForPage(qo);
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
		cardNoteMapper.deleteById(id);
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
		List<UserCardNoteTag> allTags = this.getAllTags();
		result.setAllTags(allTags);
		result.setTopTags(allTags.stream().filter(tag -> tag.getTop().equals(1)).collect(Collectors.toList()));
		return result;
	}
	
	@Override
	public ServiceResult insertTag(CardNoteTagVo vo) {
		UserCardNoteTag tagVo = new UserCardNoteTag();
		tagVo.setAdminId(SystemUtils.getSystemUserId());
		tagVo.setName(vo.getName());
		cardNoteTagMapper.insert(tagVo);
		return ServiceResult.success();
	}
	
	@Override
	public List<CardNoteListVo> randOne() {
		return cardNoteMapper.randOne(SystemUtils.getSystemUserId());
	}
	
	@Override
	public ServiceResult restore(Long id) {
		cardNoteMapper.restoreNoteById(id);
		return ServiceResult.success();
	}
	
	@Override
	public ServiceResult deleteTag(Long tagId) {
		cardNoteTagMapper.deleteById(tagId, SystemUtils.getSystemUserId());
		noteTagAssocMapper.deleteByTagId(tagId);
		return ServiceResult.success();
	}
	
	@Override
	public ServiceResult forceDelete(Long id) {
		cardNoteMapper.forceDeleteById(id);
		return ServiceResult.success();
	}
	
	@Override
	public ServiceResult updateTag(CardNoteTagVo vo) {
		return ServiceResult.success();
	}
	
	@Override
	public List<UserCardNoteTag> getAllTags() {
		return cardNoteTagMapper.selectListByAdminId(SystemUtils.getSystemUserId());
	}
	
	@Override
	public List<Long> haveTagIds() {
		return Collections.emptyList();
	}
}
