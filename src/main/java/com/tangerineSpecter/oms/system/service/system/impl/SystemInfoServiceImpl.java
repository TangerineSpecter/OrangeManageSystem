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
import com.tangerinespecter.oms.common.query.TradeStatisQueryObject;
import com.tangerinespecter.oms.common.redis.RedisKey;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.NumChainCal;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.data.StatisConvert;
import com.tangerinespecter.oms.system.convert.system.MessageConvert;
import com.tangerinespecter.oms.system.domain.dto.system.*;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.enums.MessageEnum;
import com.tangerinespecter.oms.system.domain.enums.TradeRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.pojo.ManagerInfoBean;
import com.tangerinespecter.oms.system.domain.pojo.PieChartsInfo;
import com.tangerinespecter.oms.system.domain.pojo.SystemInfoBean;
import com.tangerinespecter.oms.system.mapper.*;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import com.tangerinespecter.oms.system.service.helper.SystemHelper;
import com.tangerinespecter.oms.system.service.statis.ITradeStatisService;
import com.tangerinespecter.oms.system.service.system.ISystemInfoService;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class SystemInfoServiceImpl implements ISystemInfoService {

    private static final Integer LUCK_THRESHOLD = 70;
    /**
     * 当前系统版本
     */
    @Value("${system.version}")
    public String systemVersion;

    private final ITradeStatisService tradeStatisService;
    private final SystemHelper systemHelper;
    private final ChatHandler chatHandler;
    private final RedisHelper redisHelper;

    private final DataConstellationMapper dataConstellationMapper;
    private final SystemMenuMapper systemMenuMapper;
    private final DataTradeRecordMapper dataTradeRecordMapper;
    private final SystemNoticeMapper systemNoticeMapper;
    private final SystemBulletinMapper systemBulletinMapper;
    private final ISystemNoticeService noticeService;

    /**
     * 获取系统信息
     */
    @Override
    public SystemInfoBean getSystemInfo() {
        SystemInfoBean info = new SystemInfoBean();
        try {
            String birthday = UserContext.getBirthday();
            if (!CharSequenceUtil.isBlank(birthday)) {
                List<Integer> list = Splitter.on("-").splitToList(birthday).parallelStream().map(Integer::parseInt)
                    .collect(Collectors.toList());
                String constellation = DateUtil.getZodiac((list.get(1) - 1), list.get(2));
                DataConstellation dataConstellation = dataConstellationMapper.getConstellationByName(constellation);
                info.setAllLuck(dataConstellation.getAllLuck()).setLove(dataConstellation.getLove())
                    .setWorkLuck(dataConstellation.getWorkLuck()).setMoney(dataConstellation.getMoney())
                    .setHealth(dataConstellation.getHealth()).setStarName(dataConstellation.getName())
                    .setTodayTip(dataConstellation.getSummary());
            }

            List<SystemMenu> menuList = CollUtil.newArrayList();
            QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<SystemMenu>()
                .eq(ParamUtils.TOP, CommonConstant.IS_TOP).orderByDesc(ParamUtils.TOP_SORT);
            List<SystemMenu> menus = systemMenuMapper.selectList(queryWrapper);
            menuList.addAll(menus);
            info.setMenus(menuList);
            info.setOsName(SystemPropsUtil.get(SystemUtil.OS_NAME))
                .setSystemTitle(SystemConstant.SYSTEM_CONFIG.getHomeTitle()).setVersion(systemVersion);
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
        StatisticsInfo statisticsInfo = redisHelper.get(RedisKey.TRADE_STATIS_KEY, UserContext.getUid());
        if (statisticsInfo != null) {
            return statisticsInfo;
        }

        final StatisTradeRecord statisData = tradeStatisService.getStatisData(null);
        if (statisData == null) {
            return new StatisticsInfo();
        }

        statisticsInfo = StatisConvert.INSTANCE.convert(statisData);
        //默认展示最近30条，避免最近30天无记录
        final QueryObject<TradeStatisQueryObject> queryObject = new QueryObject<>(new TradeStatisQueryObject(), 1, 30);
        PageInfo<StatisTradeRecord> pageInfo = tradeStatisService.queryForPage(queryObject);
        for (StatisTradeRecord data : pageInfo.getList()) {
            final BigDecimal totalMoney = NumChainCal.fen2Yuan(data.getMoney(), CommonConstant.DEFAULT_CURRENCY);
            statisticsInfo.getTotalMoneys().put(data.getDate(), totalMoney);
            final BigDecimal totalIncome = NumChainCal.fen2Yuan(data.getTotalIncomeValue(), CommonConstant.DEFAULT_CURRENCY);
            statisticsInfo.getTotalIncomes().put(data.getDate(), totalIncome);
        }
        //资金分配
        final List<DataTradeRecord> tradeRecords = dataTradeRecordMapper.selectRecentListByDate(UserContext.getUid(), DateUtil.today());
        final List<PieChartsInfo> pieChartsInfos = CollUtils.convertList(tradeRecords, data -> new PieChartsInfo(TradeRecordTypeEnum
            .getType(data.getType()).getDesc(), NumChainCal.fen2Yuan(data.getEndMoney(), data.getCurrency())));
        statisticsInfo.setEndMoneyPie(pieChartsInfos);
        redisHelper.set(RedisKey.TRADE_STATIS_KEY, UserContext.getUid(), statisticsInfo);
        return statisticsInfo;
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
                result.add(MenuChildInfo.builder().title(menu.getTitle()).type(menu.getLevel()).icon(menu.getIcon())
                    .href(menu.getHref()).openType(menu.getTarget()).id(menu.getId())
                    .children(getChildMenuInfo(menu.getId(), list, permissionCodes)).build());
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
                childList.add(MenuChildInfo.builder().id(menu.getId()).title(menu.getTitle()).icon(menu.getIcon())
                    .href(menu.getHref()).openType(menu.getTarget()).permissionCode(menu.getPermissionCode())
                    .type(menu.getLevel()).build());
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
