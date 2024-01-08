package com.tangerinespecter.oms.job.message;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.enums.BotMsgTypeEnum;
import com.tangerinespecter.oms.common.enums.BotPlatformEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.system.domain.pojo.FsMarkdownCardMessage;
import com.tangerinespecter.oms.system.domain.pojo.FsSimpleCardMessage;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * 机器人消息工厂
 *
 * @author 丢失的橘子
 */
public class BotMessageFactory {

    private static final Map<BotPlatformEnum, BiFunction<BotMsgTypeEnum, IBaseMessage, String>> BOT_MSG_FUNCTION_MAP = MapUtil.newHashMap();

    static {
        BOT_MSG_FUNCTION_MAP.put(BotPlatformEnum.FS, BotMessageFactory::getFsMsgBody);
        BOT_MSG_FUNCTION_MAP.put(BotPlatformEnum.WORK_WX, (type, botMsg) -> null);
    }

    /**
     * 根据平台解析获取消息体
     *
     * @param platform 平台
     * @param type     消息类型
     * @param botMsg   机器人消息参数
     * @return 消息体
     */
    public static String getMsgBody(BotPlatformEnum platform, BotMsgTypeEnum type, IBaseMessage botMsg) {
        return Optional.ofNullable(BOT_MSG_FUNCTION_MAP.get(platform)).orElseThrow(() -> new BusinessException(RetCode.UNKNOWN_PLATFORM)).apply(type, botMsg);
    }

    private static String getFsMsgBody(BotMsgTypeEnum type, IBaseMessage botMsg) {
        if (BotMsgTypeEnum.SIMPLE.equals(type)) {
            FsSimpleCardMessage message = new FsSimpleCardMessage(botMsg.getTitle(), botMsg.getContent());
            return JSON.toJSONString(message);
        } else if (BotMsgTypeEnum.SIMPLE_MARKDOWN.equals(type)) {
            FsMarkdownCardMessage message = new FsMarkdownCardMessage(botMsg.getTitle(), botMsg.getContent());
            return JSON.toJSONString(message);
        }
        return null;
    }
}
