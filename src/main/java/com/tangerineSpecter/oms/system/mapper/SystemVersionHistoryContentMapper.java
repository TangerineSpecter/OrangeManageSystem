package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemVersionHistoryContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemVersionHistoryContentMapper extends BaseMapper<SystemVersionHistoryContent> {

    /**
     * 根据版本ID获取版本记录
     *
     * @param versionId 版本ID
     * @return
     */
    List<SystemVersionHistoryContent> selectListByVersionId(@Param("versionId") Long versionId);
}
