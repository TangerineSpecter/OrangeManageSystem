package com.tangerinespecter.oms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.query.SystemUserQueryObject;
import com.tangerinespecter.oms.system.domain.dto.system.SystemUserListDto;
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

    List<SystemUserListDto> queryForPage(SystemUserQueryObject qo);

    /**
     * 根据用户账号查询信息
     *
     * @param userName 用户名
     */
    SystemUser selectOneByUserName(@Param("userName") String userName);

    /**
     * 更新用户登录次数
     *
     * @param id            账号ID
     * @param lastLoginDate 最后登录时间
     * @param updateTime    更新时间
     */
    void updateLoginCountById(@Param("id") Long id, @Param("lastLoginDate") String lastLoginDate,
                              @Param("updateTime") String updateTime);

    /**
     * 更新管理员信息
     */
    void updateUserInfo(SystemUser info);

    /**
     * 更新管理员密码
     *
     * @param id       管理员ID
     * @param password 新密码
     */
    void updatePassword(@Param("id") Long id, @Param("password") String password);

    /**
     * 查询超级管理员账号（唯一）
     *
     * @return 超级管理员账号信息
     */
    default SystemUser selectOneByAdmin() {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<SystemUser>()
                .eq("admin", SystemConstant.IS_SYSTEM_ADMIN)
                .eq("is_del", GlobalBoolEnum.FALSE.getValue())
                .last("limit 1");
        return this.selectOne(queryWrapper);
    }
}
