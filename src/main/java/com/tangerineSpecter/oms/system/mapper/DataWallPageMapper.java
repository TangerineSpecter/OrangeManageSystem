package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.system.domain.entity.DataWallPage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 丢失的橘子
 */
@Mapper
public interface DataWallPageMapper extends BaseMapper<DataWallPage> {

    /**
     * 根据开始时间获取壁纸信息
     *
     * @param startDate 开始时间，yyyyMMdd
     * @return 壁纸信息
     */
    default DataWallPage selectOneByStartDate(Long startDate) {
        if (startDate == null) {
            return null;
        }
        return selectOne(new QueryWrapper<DataWallPage>()
                .eq("start_date", startDate)
                .last("limit 1"));
    }
}
