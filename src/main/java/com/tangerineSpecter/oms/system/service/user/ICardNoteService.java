package com.tangerinespecter.oms.system.service.user;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.system.domain.dto.user.CardNoteInfoDto;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteListVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagAssocVo;
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

    /**
     * 笔记关联标签
     *
     * @param vo 参数
     */
    void updateTag(CardNoteTagVo vo);

    /**
     * 修改标签关联
     *
     * @param vo 参数
     */
    void updateTagAssoc(CardNoteTagAssocVo vo);

    /**
     * 全部标签
     *
     * @return 标签列表
     */
    List<UserCardNoteTag> getAllTags();

    /**
     * 拥有的标签
     *
     * @return 标签列表
     */
    List<Long> haveTagIds(Long noteId);

}
