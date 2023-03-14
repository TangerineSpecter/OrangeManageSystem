package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.SystemPropsUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.MessageConstant;
import com.tangerinespecter.oms.common.constants.ParamUtils;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.netty.ChatHandler;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.system.MessageConvert;
import com.tangerinespecter.oms.system.domain.dto.system.*;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.domain.entity.DataTradeRecord;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.enums.MessageEnum;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.statis.impl.TradeStatisServiceImpl;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class SystemInfoServiceImpl implements ISystemInfoService {

    private final TradeStatisServiceImpl tradeStatisService;
    private final DataConstellationMapper dataConstellationMapper;
    private final SystemMenuMapper systemMenuMapper;
    private final DataTradeRecordMapper dataTradeRecordMapper;
    private final SystemNoticeMapper systemNoticeMapper;
    private final SystemHelper systemHelper;
    private final SystemBulletinMapper systemBulletinMapper;
    private final ISystemNoticeService noticeService;
    private final ChatHandler chatHandler;
    private static final Integer LUCK_THRESHOLD = 70;
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
            info.setOsName(SystemPropsUtil.get(SystemUtil.OS_NAME))
                    .setSystemTitle(SystemConstant.systemConfig.getHomeTitle())
                    .setVersion(systemVersion);
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
        //获取今年~至今（偏移天数）的交易数据，因为要做资金累计，需要最后一次记录的数据，如果采用时间查询会遗漏今年没有进行记录的资金数据
        long thisYearOfDay = DateUtil.betweenDay(DateUtil.beginOfYear(new Date()), new Date(), false);
//        long thisYearOfDay = 30;
        List<DataTradeRecord> thisYearTradeRecords = dataTradeRecordMapper.selectRecentListByType(UserContext.getUid(), thisYearOfDay);
        //根据时间分组
        Map<String, List<DataTradeRecord>> tradeRecordMap = CollUtils.convertMultiLinkedHashMap(thisYearTradeRecords, DataTradeRecord::getDate);
        return new StatisticsInfo(this.handlerThisYearData(tradeRecordMap));
    }

    /**
     * 处理今年起的交易数据
     *
     * @param thisYearTradeRecordMap 今年的交易数据
     * @return 统计计算结果
     */
    private List<RecordCalStatisDto> handlerThisYearData(Map<String, List<DataTradeRecord>> thisYearTradeRecordMap) {
        //key倒序从前往后累计
        List<String> reverseKey = CollUtil.reverse(CollUtil.newArrayList(thisYearTradeRecordMap.keySet()));
        //数据标记map，key：类型；value：资金
        HashMap<Integer, Integer> flagMap = MapUtil.newHashMap();
        //资金记录
        List<RecordCalStatisDto> calStatisList = CollUtil.newArrayList();
        //遍历每天数据，遍历顺序 年初 ~ 至今
        CollUtils.forEach(reverseKey, date -> {
            List<DataTradeRecord> tradeRecords = thisYearTradeRecordMap.get(date);
            //当天各类型结算，key：type，value：endMoney
            Map<Integer, Integer> endMoneyMap = CollUtils.convertMap(tradeRecords, DataTradeRecord::getType, DataTradeRecord::sumEndMoney);
            //每天投入，
            int todayInputMoney = CollUtils.convertSumList(tradeRecords, DataTradeRecord::sumInputMoney);
            //每天总收益
            int todayIncome = CollUtils.convertSumList(tradeRecords, DataTradeRecord::sumMoney2Int);
            NumChainCal numChainCal = NumChainCal.startOf(0);
            //遍历当天的每笔数据
            CollUtils.forEach(TradeRecordTypeEnum.getTypes(), type -> {
                //获取当天收盘金额，如果没有则从上一次记录的金额获取，仍然没有则为0
                Integer endMoney = endMoneyMap.getOrDefault(type, flagMap.getOrDefault(type, 0));
                //标记本次金额
                flagMap.put(type, endMoney);
                numChainCal.add(endMoney);
            });
            Integer totalEndMoney = numChainCal.getInteger();
            calStatisList.add(new RecordCalStatisDto(date, totalEndMoney, todayIncome, todayInputMoney));
        });
        return calStatisList;
    }

    /**
     * 处理最近30天资金数据
     *
     * @param statisticsInfo 数据信息
     */
    private void handlerLastThirtyData(StatisticsInfo statisticsInfo) {
        //最近天数
        final long lastDayThreshold = 30;
        //按照类型分组，每个类型取阈值条数。key：时间；value：交易数据
        Map<String, List<DataTradeRecord>> lastThirtyMap = CollUtils.convertMultiLinkedHashMap(dataTradeRecordMapper.selectRecentListByType(UserContext.getUid(), lastDayThreshold), DataTradeRecord::getDate);
        statisticsInfo.setLastThirtyDate(CollUtils.convertLimitList(lastThirtyMap.keySet(), lastDayThreshold));
        //key倒序从前往后累计
        List<String> reverseKey = CollUtil.reverse(new ArrayList<>(lastThirtyMap.keySet()));
        //数据标记map，key：类型；value：资金
        HashMap<Integer, Integer> flagMap = MapUtil.newHashMap();
        //资金记录
        List<Integer> moneyList = CollUtil.newArrayList();
        //遍历每天数据
        CollUtils.forEach(reverseKey, date -> {
            Map<Integer, Integer> endMoneyMap = CollUtils.convertMap(lastThirtyMap.get(date), DataTradeRecord::getType, DataTradeRecord::sumEndMoney);
            NumChainCal numChainCal = NumChainCal.startOf(0);
            //遍历当天的每笔数据
            CollUtils.forEach(TradeRecordTypeEnum.getTypes(), type -> {
                //获取当天收盘金额，如果没有则从上一次记录的金额获取，仍然没有则为0
                Integer endMoney = endMoneyMap.getOrDefault(type, flagMap.getOrDefault(type, 0));
                //标记本次金额
                flagMap.put(type, endMoney);
                numChainCal.add(endMoney);
            });
            moneyList.add(numChainCal.getInteger());
        });
        statisticsInfo.setLastThirtyTotalMoney(CollUtils.convertLimitList(CollUtil.reverse(moneyList), lastDayThreshold));
    }

    @Override
    public SystemNoticeInfo getNoticeInfo() {
        return new SystemNoticeInfo(systemBulletinMapper.queryRecentlyBulletinList());
    }

    @Override
    public List<MenuChildInfo> initMenu() {
        List<SystemMenu> haveMenus = systemMenuMapper.selectList(new QueryWrapper<SystemMenu>().orderByDesc("sort"));
        Set<String> permissionCodes = CollUtils.convertSet(systemHelper.getCurrentUserPermissions(), UserPermissionListDto::getCode);
        return getMenuChildInfo(haveMenus, permissionCodes);
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
        return childList;
    }

    @Override
    public List<MessageDto> message() {
        //获取当前用户消息
        PageInfo<SystemNotice> pageInfo = noticeService.queryForPage(new QueryObject<>(new SystemNoticeQueryObject(), 1, Integer.MAX_VALUE));
        return CollUtils.forEach(MessageEnum.initMessageList(), messageDto -> messageDto.setChildren(MessageConvert.INSTANCE.convert(pageInfo.getList())));
    }
}
