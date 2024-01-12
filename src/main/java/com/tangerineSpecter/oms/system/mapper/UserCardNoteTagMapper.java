package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCardNoteTagMapper extends BaseMapper<UserCardNoteTag> {

    /**
     * 获取账号的标签数量记录
     *
     * @param uid 管理员id
     * @return 标签数量
     */
    default Long selectCountByUid(String uid) {
        if (uid == null) {
            return 0L;
        }
        return selectCount(new QueryWrapper<UserCardNoteTag>()
                .eq("uid", uid)
                .eq("is_del", GlobalBoolEnum.FALSE.getValue()));
    }

    /**
     * 获取帐号所有标签
     *
     * @param uid 帐号id
     * @return 标签列表
     */
    default List<UserCardNoteTag> selectListByUid(String uid) {
        return selectList(new QueryWrapper<UserCardNoteTag>()
                .eq("is_del", GlobalBoolEnum.FALSE.getValue())
                .eq("uid", uid));
    }

    /**
     * 删除笔记标签
     *
     * @param tagId 标签id
     * @param uid   管理员id
     * @return 删除数量
     */
    default int deleteById(Long tagId, String uid) {
        return update(null, new UpdateWrapper<UserCardNoteTag>()
                .set("is_del", 1)
                .eq("id", tagId)
                .eq("uid", uid));
    }
}