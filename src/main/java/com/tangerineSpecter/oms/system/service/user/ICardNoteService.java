package com.tangerinespecter.oms.system.service.user;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagVo;

import java.util.List;

public interface ICardNoteService {


    PageInfo<CardNoteListVo> queryForPage(UserCardNoteQueryObject qo);

    void insert(CardNoteInfoVo vo);

    void delete(Long id);

    CardNoteInfoDto noteInfo();

    void insertTag(CardNoteTagVo vo);

    List<CardNoteListVo> randOne();

    void deleteTag(Long tagId);

    void restore(Long id);

    void forceDelete(Long id);

    void updateTag(CardNoteTagVo vo);

    List<UserCardNoteTag> getAllTags();

    List<Long> haveTagIds();
}
