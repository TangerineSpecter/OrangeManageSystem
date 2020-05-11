package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserHealthMapper extends BaseMapper<UserHealth> {


    List<UserHealth> queryForPage(UserHealthQueryObject qo);

    /**
     * 获取用户
     *
     * @param adminId
     * @return
     */
    List<UserHealth> queryUserWeight(@Param("adminId") Long adminId);
}