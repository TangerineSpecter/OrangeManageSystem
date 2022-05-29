package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.MessageConstant;
import com.tangerinespecter.oms.common.constants.ParamUtils;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.netty.ChatHandler;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.system.*;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.enums.TradeIncomeEnum;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private SystemNoticeMapper systemNoticeMapper;
    @Resource
    private SystemHelper systemHelper;
    @Resource
    private SystemBulletinMapper systemBulletinMapper;
    @Resource
    private ChatHandler chatHandler;

    private final Integer luck_threshold = 70;
    /**
     * 当前系统版本
     */
    @Value("${system.version}")
    public String systemVersion;

    /**
     * 获取系统信息
     */
    @Override
    public SystemInfoBean getSystemInfo() {
        SystemInfoBean info = new SystemInfoBean();
        try {
            String birthday = UserContext.getBirthday();
            if (!CharSequenceUtil.isBlank(birthday)) {
                List<Integer> list = Splitter.on("-").splitToList(birthday).parallelStream()
                        .map(Integer::parseInt).collect(Collectors.toList());
                String constellation = DateUtil.getZodiac((list.get(1) - 1), list.get(2));
                DataConstellation dataConstellation = dataConstellationMapper.getConstellationByName(constellation);
                info.setAllLuck(dataConstellation.getAllLuck())
                        .setLove(dataConstellation.getLove())
                        .setWorkLuck(dataConstellation.getWorkLuck())
                        .setMoney(dataConstellation.getMoney())
                        .setHealth(dataConstellation.getHealth())
                        .setStarName(dataConstellation.getName())
                        .setTodayTip(dataConstellation.getSummary());
            }

            List<SystemMenu> menuList = CollUtil.newArrayList();
            QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<SystemMenu>()
                    .eq(ParamUtils.TOP, CommonConstant.IS_TOP).orderByDesc(ParamUtils.TOP_SORT);
            List<SystemMenu> menus = systemMenuMapper.selectList(queryWrapper);
            menuList.addAll(menus);
            info.setMenus(menuList);
            info.setOsName(SystemUtil.get(SystemUtil.OS_NAME))
                    .setSystemTitle(SystemConstant.systemConfig.getHomeTitle())
                    .setVersion(systemVersion);
        } catch (Exception e) {
            log.error("[系统信息获取异常]:", e);
        }
        return info;
    }

    public static void main(String[] args) {
        System.out.println(TradeRecordTypeEnum.getTypes().size());
    }

    /**
     * 获取管理员相关信息
     */
    @Override
    public ManagerInfoBean getManagerInfo() {
        ManagerInfoBean info = new ManagerInfoBean();
        String birthday = UserContext.getBirthday();
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
//        List<String> permissionCodes = permissions.stream().map(UserPermissionListDto::getCode).collect(Collectors.toList());
//        Map<String, MenuChildInfo> menuInfo = getMenuChildInfo(list, permissionCodes);
//        homePageDataDto.setMenuInfo(menuInfo);
        //执行系统通知推送
        systemHelper.pushSystemNotice();
        List<SystemNotice> systemNotices = systemNoticeMapper.selectListByReadStatus(UserContext.getUid(), MessageConstant.NOT_READ);
        if (CollUtil.isNotEmpty(systemNotices)) {
            homePageDataDto.setHaveMessage(true);
        }
        return homePageDataDto;
    }

    @Override
    public StatisticsInfo getStatisticsInfo() {
        int todayIncome = dataTradeRecordMapper.getTotalIncomeByLastDay();
        String tradeLastDay = dataTradeRecordMapper.getTradeLastDay();
        int yearIncome = dataTradeRecordMapper.getTotalIncomeByLastYear();
        int monthIncome = dataTradeRecordMapper.getTotalIncomeByLastMonth();
        int weekendIncome = dataTradeRecordMapper.getTotalIncomeByDate(DateUtil.beginOfWeek(new Date()).toString(), DateUtil.endOfWeek(new Date()).toString());
        Date currentDate = new Date();
        //最近30天资金信息
        List<DataTradeRecord> lastThirtyMoneyInfo = dataTradeRecordMapper.getLastThirtyMoneyInfo();
        StatisticsInfo statisticsInfo = StatisticsInfo.builder().todayIncome(BigDecimal.valueOf(NumberUtil.div(todayIncome, 100, 2)))
                .monthIncome(BigDecimal.valueOf(NumberUtil.div(monthIncome, 100, 2)))
                .weekendIncome(BigDecimal.valueOf(NumberUtil.div(weekendIncome, 100, 2)))
                .yearIncome(BigDecimal.valueOf(NumberUtil.div(yearIncome, 100, 2)))
                .todayStatus(TradeIncomeEnum.getIncomeStatus(todayIncome)).monthStatus(TradeIncomeEnum.getIncomeStatus(monthIncome))
                .yearStatus(TradeIncomeEnum.getIncomeStatus(yearIncome)).weekendStatus(TradeIncomeEnum.getIncomeStatus(weekendIncome))
                .year(DateUtil.year(currentDate)).weekend(DateUtil.weekOfYear(currentDate)).month(DateUtil.month(currentDate) + 1)
                .today(tradeLastDay).build();
        handlerLastThirtyData(statisticsInfo, lastThirtyMoneyInfo);

        return statisticsInfo;
    }

    /**
     * 处理最近30天资金数据
     *
     * @param statisticsInfo      数据信息
     * @param lastThirtyMoneyInfo 最近资金信息
     */
    private void handlerLastThirtyData(StatisticsInfo statisticsInfo, List<DataTradeRecord> lastThirtyMoneyInfo) {
        //最近天数
        final int lastDayThreshold = 30;
        LinkedHashMap<String, List<DataTradeRecord>> lastThirtyMap = lastThirtyMoneyInfo.stream().collect(Collectors.groupingBy(DataTradeRecord::getDate, LinkedHashMap::new, Collectors.toList()));
        statisticsInfo.setLastThirtyDate(CollUtils.convertLimitList(lastThirtyMap.keySet(), lastDayThreshold));
        statisticsInfo.setLastThirtyTotalMoney(CollUtils.convertLimitList(lastThirtyMap.values(), dataTradeRecords ->
                this.sumMoney(CollUtils.convertList(dataTradeRecords, DataTradeRecord::getEndMoney)), lastDayThreshold));
    }

    /**
     * 总资金计算
     *
     * @param moneyList 资金列表
     * @return 总资金
     */
    private Integer sumMoney(List<Integer> moneyList) {
        return moneyList.stream().mapToInt(m -> m / 100).sum();
    }

    @Override
    public SystemNoticeInfo getNoticeInfo() {
        SystemNoticeInfo noticeInfo = new SystemNoticeInfo();
        List<SystemBulletin> systemBulletins = systemBulletinMapper.queryRecentlyBulletinList();
        noticeInfo.setNoticeInfos(systemBulletins);
        return noticeInfo;
    }

    @Override
    public List<MenuChildInfo> initMenu() {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, false, "sort");
        List<SystemMenu> list = systemMenuMapper.selectList(queryWrapper);
        List<UserPermissionListDto> permissions = systemHelper.getCurrentUserPermissions();
        Set<String> permissionCodes = permissions.stream().map(UserPermissionListDto::getCode).collect(Collectors.toSet());
        return getMenuChildInfo(list, permissionCodes);
    }

    /**
     * 获取菜单
     *
     * @param list            菜单集合
     * @param permissionCodes 权限code
     * @return 菜单
     */
    private List<MenuChildInfo> getMenuChildInfo(List<SystemMenu> list, Set<String> permissionCodes) {
        //找出所有一级菜单
        List<MenuChildInfo> result = CollUtil.newArrayList();
        for (SystemMenu menu : list) {
            //不在权限内容菜单跳过
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                continue;
            }
            //当前-1 为顶部菜单暂时不启用 调整为level为一级菜单
            if (menu.getLevel() == 0) {
                result.add(MenuChildInfo.builder().title(menu.getTitle()).type(menu.getLevel())
                        .icon(menu.getIcon()).href(menu.getHref()).openType(menu.getTarget())
                        .id(menu.getId()).children(getChildMenuInfo(menu.getId(), list, permissionCodes)).build());
            }
        }
        return result;
    }

    /**
     * 获取子菜单
     *
     * @param pid      父菜单
     * @param rootMenu 根目录
     */
    private List<MenuChildInfo> getChildMenuInfo(Long pid, List<SystemMenu> rootMenu, Set<String> permissionCodes) {
        if (pid == null) {
            return Collections.emptyList();
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
                        .icon(menu.getIcon()).href(menu.getHref()).openType(menu.getTarget())
                        .permissionCode(menu.getPermissionCode()).type(menu.getLevel()).build());
            }
        }
        //循环子集菜单
        for (MenuChildInfo menu : childList) {
            //不在权限内容菜单跳过
            if (!permissionCodes.contains(SystemUtils.getPermissionCode(menu.getPermissionCode()))) {
                continue;
            }
            menu.setChildren(getChildMenuInfo(menu.getId(), rootMenu, permissionCodes));
        }
        if (CollUtil.isEmpty(childList)) {
            return Collections.emptyList();
        }
        return childList;
    }

}
