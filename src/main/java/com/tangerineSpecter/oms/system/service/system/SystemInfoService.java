package com.tangerinespecter.oms.system.service.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.tangerinespecter.oms.common.constant.CommonConstant;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.dao.entity.DataConstellation;
import com.tangerinespecter.oms.system.dao.entity.SystemUser;
import com.tangerinespecter.oms.system.dao.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.dao.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 系统相关Service
 *
 * @author tangerinespecter
 * @version v0.0.3
 * @Date 2019年1月5日
 */
@Slf4j
@Service
public class SystemInfoService {
    @Resource
    private DataConstellationMapper dataConstellationMapper;

    private final Integer luck_threshold = 70;

    /**
     * 获取系统信息
     */
    public SystemInfoBean getSystemInfo() {
        SystemInfoBean info = new SystemInfoBean();
        try {
            info.setOsName(SystemUtils.getOsName()).setCpuRatio((int) SystemUtils.getCpuUsageInfo())
                    .setMemoryRatio((int) SystemUtils.getMemoryUsageInfo())
                    .setDiskRatio((int) SystemUtils.getDiskUsageInfo());
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
        DataConstellation data = dataConstellationMapper.getConstellationByName(starName);
        if (data != null) {
            info.setStarName(data.getName()).setAllLuck(data.getAllLuck()).setLove(data.getLove())
                    .setHealth(data.getHealth()).setWorkLuck(data.getWorkLuck()).setMoney(data.getMoney())
                    .setNotice(getNotice(info));
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
