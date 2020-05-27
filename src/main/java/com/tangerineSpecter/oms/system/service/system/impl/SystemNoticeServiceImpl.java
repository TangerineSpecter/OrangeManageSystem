package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.job.message.Message;
import com.tangerinespecter.oms.job.message.MessageKeys;
import com.tangerinespecter.oms.job.message.MessageTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    @Resource
    private SystemNoticeMapper systemNoticeMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void push(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        while (true) {
            try {
                int noticeCount = systemNoticeMapper.queryNotReadNoticeCount(SystemUtils.getSystemUserId());
                PrintWriter pw = response.getWriter();
                if (noticeCount > 0) {
                    pw.write("data:true\n\n");
                } else {
                    pw.write("data:false\n\n");
                }
                pw.flush();
                //检测页面关闭，断开连接
                if (pw.checkError()) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServiceResult messageSend(MessageVo vo) {
        Message message = new Message();
        BeanUtils.copyProperties(vo, message);
        message.setMessageType(MessageTypeEnum.SYSTEM_NOTICE);
        rabbitTemplate.convertAndSend(MessageKeys.SYSTEM_NOTICE_QUEUE, message);
        return ServiceResult.success();
    }
}
