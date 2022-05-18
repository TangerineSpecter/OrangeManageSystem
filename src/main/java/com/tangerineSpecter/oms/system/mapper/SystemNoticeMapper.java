package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemNoticeMapper extends BaseMapper<SystemNotice> {

    int queryNotReadNoticeCount(@Param("uid") String uid);

    /**
     * 根据管理员查询
     *
     * @param uid 管理员id
     */
    List<SystemNotice> selectListByUid(@Param("uid") String uid);

    /**
     * 根据
     *
     * @param uid    管理员ID
     * @param readStatus 阅读状态
     * @return
     */
    List<SystemNotice> selectListByReadStatus(@Param("uid") String uid, @Param("readStatus") Integer readStatus);

    List<SystemNotice> queryForPage(SystemNoticeQueryObject qo);

    /**
     * 根据ids批量修改消息删除状态
     *
     * @param ids   消息ids
     * @param isDel 删除状态
     */
    void updateDelStatusByIds(@Param("ids") List<Long> ids, @Param("isDel") Integer isDel);

    /**
     * 根据ids物理批量删除消息
     *
     * @param ids 消息ids
     */
    void deleteNoticeByIds(@Param("ids") List<Long> ids);

    /**
     * 批量更新已读状态
     *
     * @param ids        消息ids
     * @param readStatus 已读状态
     */
    void updateReadStatusByIds(@Param("ids") List<Long> ids, @Param("readStatus") Integer readStatus);
}
