package com.tangerinespecter.oms.job.schedule;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.enums.BotMsgTypeEnum;
import com.tangerinespecter.oms.common.enums.BotPlatformEnum;
import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.job.message.*;
import com.tangerinespecter.oms.system.domain.entity.SystemToken;
import com.tangerinespecter.oms.system.mapper.SystemTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息推送机器人
 *
 * @author 丢失的橘子
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendMsgBot extends AbstractJob {

    @Value("${memos.api.url}")
    private String memosApiUrl;
    private final SystemTokenMapper systemTokenMapper;

    @Override
    public String getName() {
        return "通知机器人，定时推送消息";
    }

    private String extraInfo;

    private Integer msgType;

    @Override
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    @Override
    public void execute() {
        Long botId = Convert.toLong(extraInfo);
        try {
            SystemToken info = systemTokenMapper.selectById(botId);
            log.info("[通知机器人：{}]消息推送..", info.getName());
            BotMessage botMsg = JSON.parseObject(info.getMessageInfo(), BotMessage.class);
            this.send(info.getPlatform(), info.getWebhook(), botMsg);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("机器人[{}]消息推送异常，异常信息：{}，异常：" + e, botId, e.getMessage());
            throw new BusinessException(RetCode.TASK_EXECUTE_ERROR);
        }
    }

    /**
     * 消息推送
     *
     * @param platform 平台
     * @param webhook  webhook地址
     * @param botMsg   消息体
     */
    private void send(Integer platform, String webhook, IBaseMessage botMsg) {
        final BotPlatformEnum platformEnum = IBaseDbEnum.fromValue(BotPlatformEnum.class, platform);
        final BotMsgTypeEnum msgTypeEnum = IBaseDbEnum.fromValue(BotMsgTypeEnum.class, msgType);
        final String response = HttpUtil.post(webhook, BotMessageFactory.getMsgBody(platformEnum, msgTypeEnum, botMsg));
        final BotResponse botResponse = JSON.parseObject(response, BotResponse.class);
        if (botResponse.getCode() != 0) {
            throw new BusinessException(RetCode.TASK_EXECUTE_ERROR, "推送消息异常，异常信息：错误码：{}，错误信息：{}", botResponse.getCode(), botResponse.getMsg());
        }
        log.info("消息推送完毕，推送结果：[{}]", response);
    }

    /**
     * 推送告警信息
     */
    public void sendErrorMsg(Integer code, String message, String extraInfo, Exception e) {
        try {
            String webhook = SystemConstant.SYSTEM_CONFIG.getErrorWebhook();
            if (StrUtil.isBlank(webhook)) {
                return;
            }
            final BotErrorMsg botErrorMsg = new BotErrorMsg();
            String content = "**错误码：** {}\n\n**错误描述：** {}\n\n**异常信息：** {}";
            botErrorMsg.setContent(StrUtil.format(content, code, message, StrUtil.isBlank(extraInfo) ? e.getMessage() : extraInfo));
            this.setMsgType(BotMsgTypeEnum.SIMPLE_MARKDOWN.getValue());
            this.send(BotPlatformEnum.FS.getValue(), webhook, botErrorMsg);
        } catch (Exception ignore) {
        }
    }
}
