package com.tangerinespecter.oms.system.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserHealthMapper extends BaseMapper<UserHealth> {


    List<UserHealth> queryForPage(UserHealthQueryObject qo);

    /**
     * 获取健康信息
     *
     * @param uid 管理员id
     * @return 健康信息
     */
    List<UserHealth> queryUserWeight(@Param("uid") String uid);

    /**
     * 根据记录时间查询最近一条数据
     *
     * @param recordTime 记录时间
     * @return 健康数据
     */
    default UserHealth selectOneByRecordTime(String recordTime) {
        if (StrUtil.isEmpty(recordTime)) {
            return null;
        }
        return selectOne(new QueryWrapper<UserHealth>()
                .eq("record_time", recordTime)
                .eq("is_del", GlobalBoolEnum.FALSE.getValue())
                .last("limit 1"));
    }
}