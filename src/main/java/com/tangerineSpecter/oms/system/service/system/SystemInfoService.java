package com.tangerineSpecter.oms.system.service.system;

import org.springframework.stereotype.Service;

import com.tangerineSpecter.oms.common.utils.SystemUtils;
import com.tangerineSpecter.oms.system.domain.pojo.SystemInfoBean;

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
}
