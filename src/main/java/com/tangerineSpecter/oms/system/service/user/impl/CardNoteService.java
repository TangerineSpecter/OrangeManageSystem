package com.tangerinespecter.oms.system.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.convert.user.CardNoteConvert;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteSubmitInfo;
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
    public PageInfo<CardNoteListVo> queryForPage(UserCardNoteQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<CardNoteListVo> userCardNotes = cardNoteMapper.queryForPage(qo);
        return new PageInfo<>(userCardNotes);
    }

    @Override
    public void insert(CardNoteInfoVo vo) {
        cardNoteMapper.insert(CardNoteConvert.INSTANCE.convert(vo));
    }

    @Override
    public void delete(Long id) {
        cardNoteMapper.deleteById(id);
    }

    @Override
    public CardNoteInfoDto noteInfo() {
        CardNoteInfoDto result = new CardNoteInfoDto();
        List<CardNoteSubmitInfo> submitInfos = cardNoteMapper.selectListSubmitInfo(UserContext.getUid());
        result.setSubmitInfos(submitInfos);
        result.setDay(submitInfos.size());
        result.setNoteCount(submitInfos.stream().mapToInt(CardNoteSubmitInfo::getCount).sum());
        result.setTagCount(cardNoteTagMapper.selectCountByUid(UserContext.getUid()));
        List<UserCardNoteTag> allTags = this.getAllTags();
        result.setAllTags(allTags);
        result.setTopTags(allTags.stream().filter(tag -> tag.getTop().equals(1)).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void insertTag(CardNoteTagVo vo) {
        UserCardNoteTag tagVo = new UserCardNoteTag();
        tagVo.setUid(UserContext.getUid());
        tagVo.setName(vo.getName());
        cardNoteTagMapper.insert(tagVo);
    }

    @Override
    public List<CardNoteListVo> randOne() {
        return cardNoteMapper.randOne(UserContext.getUid());
    }

    @Override
    public void restore(Long id) {
        cardNoteMapper.restoreNoteById(id);
    }

    @Override
    public void deleteTag(Long tagId) {
        cardNoteTagMapper.deleteById(tagId, UserContext.getUid());
        noteTagAssocMapper.deleteByTagId(tagId);
    }

    @Override
    public void forceDelete(Long id) {
        cardNoteMapper.forceDeleteById(id);
    }

    @Override
    public void updateTag(CardNoteTagVo vo) {
    }

    @Override
    public List<UserCardNoteTag> getAllTags() {
        return cardNoteTagMapper.selectListByUid(UserContext.getUid());
    }

    @Override
    public List<Long> haveTagIds() {
        return Collections.emptyList();
    }
}
