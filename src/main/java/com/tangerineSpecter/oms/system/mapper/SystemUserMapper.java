package com.tangerineSpecter.oms.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tangerineSpecter.oms.common.query.SystemUserQueryObject;
import com.tangerineSpecter.oms.system.domain.SystemUser;

@Mapper
public interface SystemUserMapper {

	int deleteByPrimaryKey(Long id);

	int insert(SystemUser record);

	SystemUser selectByPrimaryKey(Long id);

	List<SystemUser> selectAll();

	int updateByPrimaryKey(SystemUser record);

	SystemUser getUserByLogin(@Param("username") String username, @Param("password") String password);

	int createUserByRegister(SystemUser record);

	SystemUser getUserByUserName(@Param("username") String username);

	List<SystemUser> queryForPage(SystemUserQueryObject qo);

	Long queryForPageCount(SystemUserQueryObject qo);
}
