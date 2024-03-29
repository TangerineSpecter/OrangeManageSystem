package com.tangerinespecter.oms.system.service.system.impl;

import com.google.common.base.Splitter;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.job.message.Message;
import com.tangerinespecter.oms.job.message.MessageKeys;
import com.tangerinespecter.oms.job.message.MessageTypeEnum;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;
import com.tangerinespecter.oms.system.mapper.SystemNoticeMapper;
import com.tangerinespecter.oms.system.service.system.ISystemNoticeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

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
                long noticeCount = systemNoticeMapper.queryNotReadNoticeCount();
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
    public void messageSend(MessageVo vo) {
        Message message = new Message();
        BeanUtils.copyProperties(vo, message);
        message.setMessageType(MessageTypeEnum.SYSTEM_NOTICE);
        rabbitTemplate.convertAndSend(MessageKeys.SYSTEM_NOTICE_QUEUE, message);
    }

    @Override
    public List<SystemNotice> list(SystemNoticeQueryObject qo) {
        return systemNoticeMapper.queryForPage(qo);
    }

    @Override
    public long batchUpdateDelStatus(NoticeUpdateStatusVo vo) {
        List<Long> ids = Splitter.on(",").omitEmptyStrings().splitToList(vo.getIds())
                .parallelStream().map(Long::parseLong).collect(Collectors.toList());
        systemNoticeMapper.updateDelStatusByIds(ids, vo.getIsDel());
        return systemNoticeMapper.queryNotReadNoticeCount();
    }

    @Override
    public void batchClear(NoticeUpdateStatusVo vo) {
        List<Long> ids = Splitter.on(",").omitEmptyStrings().splitToList(vo.getIds())
                .parallelStream().map(Long::parseLong).collect(Collectors.toList());
        systemNoticeMapper.deleteNoticeByIds(ids);
    }

    @Override
    public long batchUpdateReadStatus(NoticeUpdateStatusVo vo) {
        List<Long> ids = Splitter.on(",").omitEmptyStrings().splitToList(vo.getIds())
                .parallelStream().map(Long::parseLong).collect(Collectors.toList());
        systemNoticeMapper.updateReadStatusByIds(ids, vo.getReadStatus());
        return systemNoticeMapper.queryNotReadNoticeCount();
    }

    @Override
    public SystemNotice getNoticeInfo(Long id) {
        return systemNoticeMapper.selectById(id);
    }
}
