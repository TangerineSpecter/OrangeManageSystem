package com.tangerineSpecter.oms.system.service.system;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerineSpecter.oms.common.constant.CommonConstant;
import com.tangerineSpecter.oms.common.constant.RetCode;
import com.tangerineSpecter.oms.common.query.SystemUserQueryObject;
import com.tangerineSpecter.oms.common.result.ServiceResult;
import com.tangerineSpecter.oms.common.utils.MD5Utils;
import com.tangerineSpecter.oms.common.utils.ServiceKey;
import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.domain.pojo.AccountsInfo;
import com.tangerineSpecter.oms.system.mapper.SystemUserMapper;
import com.tangerineSpecter.oms.system.service.page.PageResultService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private PageResultService pageResultService;

	/**
	 * 校验登录
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public ServiceResult verifyLogin(AccountsInfo model) {
		String md5Pwd;
		try {
			md5Pwd = MD5Utils.getMD5Pwd(model.getPassword(), CommonConstant.SALT);
			UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), md5Pwd);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
		} catch (UnknownAccountException e) {
			log.error(String.format("帐号登录异常：%s", e));
			return ServiceResult.fail(RetCode.ACCOUNTS_NOT_EXSIT_CODE, RetCode.ACCOUNTS_NOT_EXSIT_CODE_DESC);
		} catch (NoSuchAlgorithmException e) {
			log.error(String.format("帐号登录异常：%s", e));
			return ServiceResult.fail(RetCode.ACCOUNTS_PASSWORD_ERROR_CODE, RetCode.ACCOUNTS_PASSWORD_ERROR_CODE_DESC);
		} catch (IncorrectCredentialsException e) {
			log.error(String.format("帐号登录异常：%s", e));
			return ServiceResult.fail(RetCode.ACCOUNTS_PASSWORD_ERROR_CODE, RetCode.ACCOUNTS_PASSWORD_ERROR_CODE_DESC);
		}
		return ServiceResult.success();
	}

	/**
	 * 后台管理员列表
	 */
	public void querySystemUserList(Model model, SystemUserQueryObject qo) {
		PageHelper.startPage(qo.getPage(), qo.getSize());
		List<SystemUser> pageList = systemUserMapper.queryForPage(qo);
		//得到分页结果对象
		PageInfo<SystemUser> systemUserInfo = new PageInfo<>(pageList);
		pageResultService.queryForPage(model, systemUserInfo.getList(), systemUserInfo.getTotal(), qo.getPage(),
				ServiceKey.System.SYSTEM_USER_PAGE_LIST);
	}

	/**
	 * 获取管理员信息
	 */
	public void getSystemInfo(Model model, Long id) {
		model.addAttribute("systemUserInfo", systemUserMapper.selectByPrimaryKey(id));
	}

	/**
	 * 更新账户信息
	 */
	@Transactional
	public ServiceResult updateSystemUserInfo(SystemUser systemUser) {
		systemUserMapper.updateByPrimaryKey(systemUser);
		return ServiceResult.success();
	}
}
