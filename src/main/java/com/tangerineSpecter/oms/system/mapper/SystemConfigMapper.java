package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfig> {
    /**
     * 获取最近一条系统配置
     *
     * @return
     */
    SystemConfig queryLastSystemConfig();

    /**
     * 删除所有配置信息
     */
    void deleteConfigAll();
}
