package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.constants.TradeConstant;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.*;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import com.tangerinespecter.oms.system.mapper.DataTradeRecordMapper;
import com.tangerinespecter.oms.system.mapper.SystemBulletinMapper;
import com.tangerinespecter.oms.system.mapper.SystemMenuMapper;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private DataTradeRecordMapper dataTradeRecordMapper;
    @Resource
    private SystemHelper systemHelper;
    @Resource
    private SystemBulletinMapper systemBulletinMapper;

    private final Integer luck_threshold = 70;

    /**
     * 获取系统信息
     */
    @Override
    public SystemInfoBean getSystemInfo() {
        SystemInfoBean info = new SystemInfoBean();
        try {
            SystemUser currentUser = SystemUtils.getCurrentUser();
            String birthday = currentUser.getBirthday();
            if (!StrUtil.isBlank(birthday)) {
                List<Integer> list = Splitter.on("-").splitToList(birthday).parallelStream()
                        .map(Integer::parseInt).collect(Collectors.toList());
                String constellation = DateUtil.getZodiac((list.get(1) - 1), list.get(2));
                DataConstellation dataConstellation = dataConstellationMapper.getConstellationByName(constellation);
                info.setAllLuck(dataConstellation.getAllLuck())
                        .setLove(dataConstellation.getLove())
                        .setWorkLuck(dataConstellation.getWorkLuck())
                        .setMoney(dataConstellation.getMoney())
                        .setHealth(dataConstellation.getHealth())
                        .setStarName(dataConstellation.getName());
            }
            info.setOsName(SystemUtil.get(SystemUtil.OS_NAME)).setSystemTitle("橘子系统")
                    .setVersion(SystemConstant.SYSTEM_VERSION);
        } catch (Exception e) {
            log.error("[系统信息获取异常]:", e);
        }
        return info;
    }

    public static void main(String[] args) {
        SystemUtil.dumpSystemInfo();
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
                    .setHealth(data.getHealth()).setWorkLuck(data.getWorkLuck()).setMoney(data.getMoney());
        }
        return info;
    }


    @Override
    public HomePageDataDto initHome() {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, false, "sort");
        List<SystemMenu> list = systemMenuMapper.selectList(queryWrapper);
        List<UserPermissionListDto> permissions = systemHelper.getCurrentUserPermissions();
        HomePageDataDto homePageDataDto = new HomePageDataDto();
        if (permissions.isEmpty()) {
            return homePageDataDto;
        }
        List<String> permissionCodes = permissions.stream().map(UserPermissionListDto::getCode).collect(Collectors.toList());
        Map<String, MenuChildInfo> menuInfo = getMenuChildInfo(list, permissionCodes);
        homePageDataDto.setMenuInfo(menuInfo);
        return homePageDataDto;
    }

    @Override
    public StatisticsInfo getStatisticsInfo() {
        int todayIncome = dataTradeRecordMapper.getTotalIncomeByLastDay();
        String tradeLastDay = dataTradeRecordMapper.getTradeLastDay();
        int monthIncome = dataTradeRecordMapper.getTotalIncomeByLastMonth();
        int weekendIncome = dataTradeRecordMapper.getTotalIncomeByDate(DateUtil.beginOfWeek(new Date()).toString(), DateUtil.endOfWeek(new Date()).toString());
        List<DataTradeRecord> lastThirtyMoneyInfo = dataTradeRecordMapper.getLastThirtyMoneyInfo();
        List<Integer> totalMoneyList = lastThirtyMoneyInfo.stream().map(DataTradeRecord::getEndMoney).collect(Collectors.toList());
        List<String> dateList = lastThirtyMoneyInfo.stream().map(DataTradeRecord::getDate).collect(Collectors.toList());
        return StatisticsInfo.builder().todayIncome(BigDecimal.valueOf(NumberUtil.div(todayIncome, 100, 2)))
                .monthIncome(BigDecimal.valueOf(NumberUtil.div(monthIncome, 100, 2)))
                .weekendIncome(BigDecimal.valueOf(NumberUtil.div(weekendIncome, 100, 2)))
                .todayStatus(todayIncome >= 0 ? TradeConstant.TRADE_STATUS_PROFIT : TradeConstant.TRADE_STATUS_LOSS)
                .monthStatus(monthIncome >= 0 ? TradeConstant.TRADE_STATUS_PROFIT : TradeConstant.TRADE_STATUS_LOSS)
                .weekendStatus(weekendIncome >= 0 ? TradeConstant.TRADE_STATUS_PROFIT : TradeConstant.TRADE_STATUS_LOSS)
                .weekend(DateUtil.weekOfYear(new Date())).month(DateUtil.month(new Date()) + 1)
                .today(tradeLastDay).lastThirtyTotalMoney(totalMoneyList).lastThirtyDate(dateList).build();
    }

    @Override
    public SystemNoticeInfo getNoticeInfo() {
        SystemNoticeInfo noticeInfo = new SystemNoticeInfo();
        List<SystemBulletin> systemBulletins = systemBulletinMapper.queryRecentlyBulletinList();
        noticeInfo.setNoticeInfos(systemBulletins);
        return noticeInfo;
    }

    /**
     * 获取菜单
     *
     * @param list            菜单集合
     * @param permissionCodes 权限code
     * @return 菜单
     */
    private Map<String, MenuChildInfo> getMenuChildInfo(List<SystemMenu> list, List<String> permissionCodes) {
        //找出所有一级菜单
        Map<String, MenuChildInfo> menuMap = new LinkedHashMap<>();
        for (SystemMenu menu : list) {
            //不在权限内容菜单跳过
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                continue;
            }
            if (menu.getPid() == null || menu.getPid() == -1) {
                menuMap.put(menu.getTitle(), MenuChildInfo.builder().title(menu.getTitle())
                        .icon(menu.getIcon()).href(menu.getHref()).target(menu.getTarget())
                        .id(menu.getId()).child(getChildMenuInfo(menu.getId(), list, permissionCodes)).build());
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
    private List<MenuChildInfo> getChildMenuInfo(Long pid, List<SystemMenu> rootMenu, List<String> permissionCodes) {
        if (pid == null) {
            return null;
        }
        //子菜单
        List<MenuChildInfo> childList = new ArrayList<>();
        for (SystemMenu menu : rootMenu) {
            //不在权限内容菜单跳过
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                continue;
            }
            if (pid.equals(menu.getPid())) {
                childList.add(MenuChildInfo.builder().id(menu.getId()).title(menu.getTitle())
                        .icon(menu.getIcon()).href(menu.getHref()).target(menu.getTarget())
                        .permissionCode(menu.getPermissionCode()).build());
            }
        }
        //循环子集菜单
        for (MenuChildInfo menu : childList) {
            //不在权限内容菜单跳过
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                continue;
            }
            menu.setChild(getChildMenuInfo(menu.getId(), rootMenu, permissionCodes));
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
