package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.UserCardNoteTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCardNoteTagMapper extends BaseMapper<UserCardNoteTag> {

    /**
     * 获取账号的标签数量记录
     *
     * @param adminId 管理员id
     * @return 标签数量
     */
    default int selectCountByAdminId(Long adminId) {
        if (adminId == null) {
            return 0;
        }
        return selectCount(new QueryWrapper<UserCardNoteTag>()
                .eq("admin_id", adminId)
                .eq("is_del", 0));
    }

    /**
     * 获取帐号所有标签
     *
     * @param adminId 帐号id
     * @return 标签列表
     */
    default List<UserCardNoteTag> selectListByAdminId(Long adminId) {
        return selectList(new QueryWrapper<UserCardNoteTag>()
                .eq("is_del", 0)
                .eq("admin_id", adminId));
    }
}