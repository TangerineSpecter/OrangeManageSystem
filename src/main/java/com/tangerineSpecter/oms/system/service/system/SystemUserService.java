package com.tangerineSpecter.oms.system.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangerineSpecter.oms.common.constant.RetCode;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsInfo;
import com.tangerineSpecter.oms.system.mapper.SystemUserMapper;

@Service
public class SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;

	/**
	 * 校验登录
	 */
	public ServiceResult verifyLogin(AccountsInfo model) {
		SystemUser systemUser = systemUserMapper.getUserByUserName(model.getUsername());
		SystemUser userNotExsit = systemUserMapper.getUserByLogin(model.getUsername(), model.getPassword());
		if (systemUser == null) {
			return ServiceResult.fail(RetCode.ACCOUNTS_NOT_EXSIT_CODE, RetCode.ACCOUNTS_NOT_EXSIT_DESC);
		} else if (userNotExsit == null) {
			return ServiceResult.fail(RetCode.ACCOUNTS_PASSWORD_ERROR_CODE, RetCode.ACCOUNTS_PASSWORD_ERROR_DESC);
		}
		return ServiceResult.success();
	}
	
	/**
	 * 后台管理员列表
	 */
	public List<SystemUser> querySystemUserList() {
		return systemUserMapper.selectAll();
	}
}
