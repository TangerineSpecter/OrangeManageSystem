package com.tangerineSpecter.oms.system.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.tangerineSpecter.oms.common.constant.RetCode;
import com.tangerineSpecter.oms.common.service.ServiceResult;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsParam;
import com.tangerineSpecter.oms.system.mapper.SystemUserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;

	/**
	 * 校验登录
	 */
	public ServiceResult verifyLogin(AccountsParam model) {
		SystemUser systemUser = systemUserMapper.getUserByUserName(model.getUsername());
		SystemUser userNotExsit = systemUserMapper.getUserByLogin(model.getUsername(), model.getPassword());
		if (systemUser == null) {
			return ServiceResult.fail(RetCode.ACCOUNTS_NOT_EXSIT_CODE, RetCode.ACCOUNTS_NOT_EXSIT_DESC);
		} else if (userNotExsit == null) {
			return ServiceResult.fail(RetCode.ACCOUNTS_PASSWORD_ERROR_CODE, RetCode.ACCOUNTS_PASSWORD_ERROR_DESC);
		}
		return ServiceResult.success();
	}
}
