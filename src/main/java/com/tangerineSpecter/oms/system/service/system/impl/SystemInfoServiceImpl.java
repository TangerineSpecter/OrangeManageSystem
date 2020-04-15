package com.tangerinespecter.oms.system.service.system.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.HomePageDataDto;
import com.tangerinespecter.oms.system.domain.dto.system.MenuChildInfo;
import com.tangerinespecter.oms.system.domain.dto.system.StatisticsInfo;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import com.tangerinespecter.oms.system.mapper.SystemMenuMapper;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统相关Service
 *
 * @author tangerinespecter
 * @version v0.0.3
 * @Date 2019年1月5日
 */
@Slf4j
@Service
public class SystemInfoServiceImpl implements ISystemInfoService {
    @Resource
    private DataConstellationMapper dataConstellationMapper;
    @Resource
    private SystemMenuMapper systemMenuMapper;

    private final Integer luck_threshold = 70;

    /**
     * 获取系统信息
     */
    @Override
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
    @Override
    public ManagerInfoBean getManagerInfo() {
        ManagerInfoBean info = new ManagerInfoBean();
        SystemUser systemUser = SystemUtils.getCurrentUser();
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

    @Override
    public HomePageDataDto initHome() {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, false, "sort");
        List<SystemMenu> list = systemMenuMapper.selectList(queryWrapper);
        return HomePageDataDto.builder().menuInfo(getMenuChildInfo(list)).build();
    }

    @Override
    public StatisticsInfo getStatisticsInfo() {
        return StatisticsInfo.builder().todayIncome(new BigDecimal(1111)).build();
    }

    /**
     * 获取菜单
     *
     * @param list 菜单集合
     * @return 菜单
     */
    private Map<String, MenuChildInfo> getMenuChildInfo(List<SystemMenu> list) {
        //找出所有一级菜单
        Map<String, MenuChildInfo> menuMap = new LinkedHashMap<>();
        for (SystemMenu menu : list) {
            if (menu.getPid() == null || menu.getPid() == -1) {
                menuMap.put(menu.getTitle(), MenuChildInfo.builder().title(menu.getTitle())
                        .icon(menu.getIcon()).href(menu.getHref()).target(menu.getTarget())
                        .id(menu.getId()).child(getChildMenuInfo(menu.getId(), list)).build());
            }
        }
        return menuMap;
    }

    /**
     * 获取子菜单
     *
     * @param pid      父菜单
     * @param rootMenu 根目录
     */
    private List<MenuChildInfo> getChildMenuInfo(Long pid, List<SystemMenu> rootMenu) {
        if (pid == null) {
            return null;
        }
        //子菜单
        List<MenuChildInfo> childList = new ArrayList<>();
        for (SystemMenu menu : rootMenu) {
            if (pid.equals(menu.getPid())) {
                childList.add(MenuChildInfo.builder().id(menu.getId()).title(menu.getTitle())
                        .icon(menu.getIcon()).href(menu.getHref()).target(menu.getTarget())
                        .build());
            }
        }
        //循环子集菜单
        for (MenuChildInfo menu : childList) {
            menu.setChild(getChildMenuInfo(menu.getId(), rootMenu));
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
