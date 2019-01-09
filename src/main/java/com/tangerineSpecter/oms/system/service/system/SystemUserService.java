package com.tangerineSpecter.oms.system.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tangerineSpecter.oms.common.constant.RetCode;
import com.tangerineSpecter.oms.common.query.SystemUserQueryObject;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsInfo;
import com.tangerineSpecter.oms.system.mapper.SystemUserMapper;
import com.tangerineSpecter.oms.system.service.page.PageResultService;

@Service
public class SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private PageResultService pageResultService;

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
	public void querySystemUserList(Model model, SystemUserQueryObject qo) {
		pageResultService.queryForPage(model, systemUserMapper.queryForPage(qo), systemUserMapper.queryForPageCount(qo),
				qo.getPage(), ServiceKey.System.SYSTEM_USER_PAGE_LIST);
	}
}
