package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
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
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.system.MessageConvert;
import com.tangerinespecter.oms.system.domain.dto.system.*;
import com.tangerinespecter.oms.system.domain.entity.*;
import com.tangerinespecter.oms.system.domain.enums.MessageEnum;
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
        //获取今年至今的交易数据
        Map<String, List<DataTradeRecord>> tradeRecordMap = CollUtils.convertMultiLinkedHashMap(dataTradeRecordMapper.selectListByThisYear(UserContext.getUid()), DataTradeRecord::getDate);
        Map<String, Integer> totalIncomeMap = CollUtils.convertLinkedMap(tradeRecordMap.keySet(), key -> key, key -> tradeStatisService.sumIncome(tradeRecordMap.get(key)));
        StatisticsInfo statisticsInfo = new StatisticsInfo(totalIncomeMap);
        //最近30天资金信息
        this.handlerLastThirtyData(statisticsInfo);
        return statisticsInfo;
    }

    /**
     * 处理最近30天资金数据
     *
     * @param statisticsInfo 数据信息
     */
    private void handlerLastThirtyData(StatisticsInfo statisticsInfo) {
        //最近天数
        final int lastDayThreshold = 30;
        //TODO 待优化，每次查询出所有的数据
        Map<String, List<DataTradeRecord>> lastThirtyMap = CollUtils.convertMultiLinkedHashMap(dataTradeRecordMapper.getLastThirtyMoneyInfo(UserContext.getUid()), DataTradeRecord::getDate);
        statisticsInfo.setLastThirtyDate(CollUtils.convertLimitList(lastThirtyMap.keySet(), lastDayThreshold));
        statisticsInfo.setLastThirtyTotalMoney(CollUtils.convertLimitList(lastThirtyMap.values(), tradeStatisService::sumMoney, lastDayThreshold));
    }

    @Override
    public SystemNoticeInfo getNoticeInfo() {
        List<SystemBulletin> systemBulletins = systemBulletinMapper.queryRecentlyBulletinList();
        return new SystemNoticeInfo(systemBulletins);
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
        PageInfo<SystemNotice> pageInfo = noticeService.queryForPage(new SystemNoticeQueryObject(true));
        List<MessageDto.ChildrenDto> messageInfos = MessageConvert.INSTANCE.convert(pageInfo.getList());
        List<MessageDto> result = MessageEnum.initMessageList();
        List<MessageDto.ChildrenDto> notReadMessage = CollUtils.filterList(messageInfos, message -> MessageConstant.NOT_READ.equals(message.getReadStatus()));
        List<MessageDto.ChildrenDto> systemMessage = CollUtils.filterList(messageInfos, message -> MessageConstant.SYSTEM_NOTICE.equals(message.getType()));
        for (MessageDto messageDto : result) {
            if (MessageEnum.ALL_NOTICE.getValue().equals(messageDto.getId())) {
                messageDto.setChildren(messageInfos);
            }
            if (MessageEnum.NOT_READ_NOTICE.getValue().equals(messageDto.getId())) {
                messageDto.setChildren(notReadMessage);
            }
            if (MessageEnum.SYSTEM_NOTICE.getValue().equals(messageDto.getId())) {
                messageDto.setChildren(systemMessage);
            }
        }
        return result;
    }
}
