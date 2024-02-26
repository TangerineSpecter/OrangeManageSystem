package com.tangerinespecter.oms.system.service.user.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.convert.user.CardNoteConvert;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteSubmitInfo;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagAssocVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagVo;
import com.tangerinespecter.oms.system.mapper.UserCardNoteMapper;
import com.tangerinespecter.oms.system.mapper.UserCardNoteTagMapper;
import com.tangerinespecter.oms.system.mapper.UserNoteTagAssocMapper;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 丢失的橘子
 */
@Service
@RequiredArgsConstructor
public class CardNoteServiceImpl implements ICardNoteService {

    private final UserCardNoteMapper cardNoteMapper;
    private final UserCardNoteTagMapper cardNoteTagMapper;
    private final UserNoteTagAssocMapper noteTagAssocMapper;

    @Override
    public List<CardNoteListVo> list(UserCardNoteQueryObject qo) {
        return cardNoteMapper.queryForPage(qo);
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
        //TODO 更新标签名称
    }

    @Override
    public void updateTagAssoc(CardNoteTagAssocVo vo) {
        noteTagAssocMapper.deleteByNoteId(vo.getId());
        if (CharSequenceUtil.isEmpty(vo.getNoteTag())) {
            return;
        }
        List<Long> tagIds = CharSequenceUtil.split(vo.getNoteTag(), ',', -1, true, Long::parseLong);
        noteTagAssocMapper.batchInsert(vo.getId(), tagIds);
    }

    @Override
    public List<UserCardNoteTag> getAllTags() {
        return cardNoteTagMapper.selectListByUid(UserContext.getUid());
    }

    @Override
    public List<Long> haveTagIds(Long noteId) {
        return noteTagAssocMapper.selectTagIdsByNoteId(noteId);
    }
}
