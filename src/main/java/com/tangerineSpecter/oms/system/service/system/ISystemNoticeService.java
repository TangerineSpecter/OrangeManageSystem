package com.tangerinespecter.oms.system.service.system;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.SystemNoticeQueryObject;
import com.tangerinespecter.oms.system.domain.entity.SystemNotice;
import com.tangerinespecter.oms.system.domain.vo.system.MessageVo;
import com.tangerinespecter.oms.system.domain.vo.system.NoticeUpdateStatusVo;

import javax.servlet.http.HttpServletResponse;

public interface ISystemNoticeService {

    /**
     * 消息推送
     *
     * @param response
     */
    void push(HttpServletResponse response);

    void messageSend(MessageVo vo);

    PageInfo<SystemNotice> queryForPage(SystemNoticeQueryObject qo);

    void batchUpdateDelStatus(NoticeUpdateStatusVo ids);

    void batchClear(NoticeUpdateStatusVo ids);

    void batchUpdateReadStatus(NoticeUpdateStatusVo ids);

    /**
     * 获取消息详情
     *
     * @param id 消息ID
     * @return 消息内容
     */
    SystemNotice getNoticeInfo(Long id);
}
