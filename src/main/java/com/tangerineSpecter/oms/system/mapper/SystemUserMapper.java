package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    SystemUser getUserByLogin(@Param("userName") String username, @Param("password") String password);

    int createUserByRegister(SystemUser record);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     */
    SystemUser getUserByUserName(@Param("userName") String username);

    List<SystemUser> queryForPage(SystemUserQueryObject qo);

    /**
     * 根据用户账号查询信息
     *
     * @param userName 用户名
     */
    SystemUser selectOneByUserName(@Param("userName") String userName);

    /**
     * 更新用户登录次数
     *
     * @param id 账号ID
     * @param lastLoginDate 最后登录时间
     * @param updateTime 更新时间
     */
    void updateLoginCountById(@Param("id") Long id, @Param("lastLoginDate") String lastLoginDate,
                              @Param("updateTime") String updateTime);
}
