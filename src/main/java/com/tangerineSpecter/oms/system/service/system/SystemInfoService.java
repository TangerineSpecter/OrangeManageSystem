package com.tangerineSpecter.oms.system.service.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangerineSpecter.oms.common.utils.DateUtils;
import com.tangerineSpecter.oms.common.utils.SystemUtils;
import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.domain.SystemUser;
import com.tangerineSpecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerineSpecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerineSpecter.oms.system.mapper.DataConstellationMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统相关Service
 * 
 * @author TangerineSpecter
 * @Date 2019年1月5日
 * @version v0.0.3
 */
@Slf4j
@Service
public class SystemInfoService {
	@Autowired
	private DataConstellationMapper dataConstellationMapper;

	/**
	 * 获取系统信息
	 */
	public SystemInfoBean getSystemInfo() {
		SystemInfoBean info = new SystemInfoBean();
		try {
			info.setOsName(SystemUtils.getOsName());
			info.setCpuRatio((int) SystemUtils.getCpuUsageInfo());
			info.setMemoryRatio((int) SystemUtils.getMemoryUsageInfo());
			info.setDiskRatio((int) SystemUtils.getDiskUsageInfo());
		} catch (Exception e) {
			log.error("系统信息获取异常，{}", e);
		}
		return info;
	}

	/**
	 * 获取管理员相关信息
	 */
	public ManagerInfoBean getManagerInfo() {
		ManagerInfoBean info = new ManagerInfoBean();
		SystemUser systemUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();
		String birthday = systemUser.getBirthday();
		String starName = DateUtils.getStarNameByDate(birthday);
		DataConstellation data = dataConstellationMapper.getConstellactionByName(starName);
		if (data != null) {
			info.setStarName(data.getName());
			info.setAllLuck(data.getAllLuck());
			info.setLove(data.getLove());
			info.setHealth(data.getHealth());
			info.setWorkLuck(data.getWorkLuck());
			info.setMoney(data.getMoney());
		}
		return info;
	}
}
