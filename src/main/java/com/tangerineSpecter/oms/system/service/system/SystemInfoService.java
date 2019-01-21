package com.tangerineSpecter.oms.system.service.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.tangerineSpecter.oms.common.constant.CommonConstant;
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

	private final Integer luck_threshold = 70;

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
			log.error("[系统信息获取异常]:", e);
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
			info.setNotice(getNotice(info));
		}
		return info;
	}

	/**
	 * 获取公告
	 */
	private String getNotice(ManagerInfoBean info) {
		String notice = CommonConstant.NULL_KEY_STR;
		if (Integer.valueOf(info.getHealth().substring(0, info.getHealth().length() - 1)) >= luck_threshold) {
			notice += "是个身体健康的好日子；";
		}
		if (Integer.valueOf(info.getMoney().substring(0, info.getMoney().length() - 1)) >= luck_threshold) {
			notice += "走在路上或许可以捡到钱；";
		}
		if (Integer.valueOf(info.getLove().substring(0, info.getLove().length() - 1)) >= luck_threshold) {
			notice += "好像会犯桃花哟；";
		}
		if (Integer.valueOf(info.getWorkLuck().substring(0, info.getWorkLuck().length() - 1)) >= luck_threshold) {
			notice += "工作热情度很高的一天；";
		}
		if (StringUtils.isEmpty(notice)) {
			notice = "今天是平平淡淡的一天！";
		}
		return notice;
	}
}
